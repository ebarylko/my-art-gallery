(ns my-art-gallery.fb.firestore
  (:require [my-art-gallery.fb.core :as fb]
            [re-frame.core :as re-frame]
            [clojure.set :as st]
            [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as cske]
            ["firebase/firestore/lite" :as fs]))

(defn db []
  (fs/getFirestore @fb/firebase-instance))

(defn document->clj
  "Returns a pair [id document] from the Firestore document argument"
  [doc]
  [(aget doc "id")
   (-> doc
       (js-invoke "data")
       (js->clj :keywordize-keys true)
       (#(cske/transform-keys csk/->kebab-case %)))])

(defn snapshot->clj [snapshot]
  (map (fn [doc] (document->clj doc))
       (.-docs ^js snapshot)))

(defn fetch-collection [path on-success on-error]
  (-> (db)
      (fs/collection path)
      fs/getDocs
      (.then (comp on-success snapshot->clj))
      (.catch on-error)))

(defn doc->clj [doc]
  (.data doc))

(defn fetch-doc [path on-success on-error]
  (-> (db)
      (fs/doc path)
      fs/getDoc
      (.then (comp on-success document->clj))
      (.catch on-error)))

