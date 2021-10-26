(ns my-art-gallery.fb.firestore
  (:require [my-art-gallery.fb.core :as fb]
            [re-frame.core :as re-frame]
            [clojure.set :as st]
            ["firebase/firestore/lite" :as fs]))

(defn db []
  (fs/getFirestore @fb/firebase-instance))

(defn coll-ref [path]
  (fs/collection (db) path))

(defn document->clj [doc]
  [(aget doc "id")
   (js->clj (js-invoke doc "data") :keywordize-keys true)])

(defn snapshot->clj [snapshot]
  (map (fn [doc] (document->clj doc))
       (.-docs ^js snapshot)))

(defn doc-ref
  ([path] (.doc (db) path))
  ([collection id]
   (-> (coll-ref collection) (.doc id))))

(defn document-add
  "Adds a document to the collection creating new key
  If collection does not exist it is created.
  Returns a Promise resolving to the document ref."
  [{:keys [:path :collection :document]}]
  (-> (coll-ref (or path collection))
      (.add (clj->js document))))

(defn document-set
  ([{:keys [:path :collection :id :document]} {:keys [:merge] :as opts}]
   (js-invoke (-> (coll-ref (or path collection))
                  (.doc id))
              "set" (clj->js document) (clj->js opts)))
  ([args]
   (document-set args {})))

(defn query [ref]
  (.get ref))


(defn ->gallery [[id glr]]
  [id
   (st/rename-keys glr {:paintingUrl :painting-url
                        :createdOn :created-on
                        :avatarUrl :avatar-url})])


(defn get-collection [collection on-success on-error]
  (-> collection
      coll-ref
      fs/getDocs
      (.then #(re-frame/dispatch-sync [on-success (map ->gallery (snapshot->clj %))]))
      (.catch #(re-frame/dispatch-sync [on-error]))))

(defn get-galleries [on-success on-error]
  (-> "galleries"
      (get-collection on-success on-error)))

(defn get-galleries1 []
  (-> (db)
      (fs/collection "galleries")
      fs/getDocs
      (.then #(println "Got galleries" (snapshot->clj %)))
      (.catch #(println "Error getting galleries :( !"))))

(defn get-document [collection id]
  (query (doc-ref collection id)))

(defn get-document-field-value [doc field]
  (aget (.data doc) field))

(defn delete-document
  ([path]
   (.delete (doc-ref path)))
  ([collection id]
   (.delete (doc-ref collection id))))

(defn where [coll-ref & [operator k value]]
  (fs/where coll-ref k operator value))

(defn order-by [coll-ref field & direction]
  (apply fs/orderBy coll-ref field direction))

(defn start-at [coll-ref index]
  (fs/startAt coll-ref index))

(defn start-after [coll-ref index]
  (fs/startAfter coll-ref index))

(defn limit [coll-ref n]
  (fs/limit coll-ref n))

