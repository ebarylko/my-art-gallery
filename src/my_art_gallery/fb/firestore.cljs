(ns my-art-gallery.fb.firestore
  (:require [my-art-gallery.fb.core :as fb]
            ["firebase/firestore/lite" :as fs]))

(defn db []
  (fs/getFirestore @fb/firebase-instance))

(defn coll-ref [path]
  (fs/collection (db) path))

(defn document->clj [doc]
  {(aget doc "id") (js->clj (js-invoke doc "data"))})

(defn snapshot->clj [snapshot]
  (map (fn [doc] (document->clj doc))
       (.-docs ^js snapshot)))

(defn get-galleries []
  (-> (db)
      (fs/collection "galleries")
      fs/getDocs
      (.then #(println "Got galleries" (snapshot->clj %)))
      (.catch #(println "Error getting galleries :( !"))
      ))

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

(defn get-collection [collection]
  (query (coll-ref collection)))

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
  (js-invoke coll-ref "where" k operator value))

(defn order-by [coll-ref field & direction]
  (apply js-invoke coll-ref "orderBy" field direction))

(defn start-at [coll-ref index]
  (js-invoke coll-ref "startAt" index))

(defn start-after [coll-ref index]
  (js-invoke coll-ref "startAfter" index))

(defn limit [coll-ref n]
  (js-invoke coll-ref "limit" n))

