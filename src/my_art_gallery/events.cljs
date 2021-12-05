(ns my-art-gallery.events
  (:require
   [clojure.set :as st]
   [re-frame.core :as re-frame]
   [my-art-gallery.db :as db]
   [my-art-gallery.fb.firestore :as fbf]
   [my-art-gallery.fb.events :as fbe]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(defn fetch-for
  [path key]
  {:path (clojure.string/join "/" path)
   :success [::fetch-done key]
   :error [::fetch-done :error]})


(re-frame/reg-event-fx
 ::load-painting
 (fn [_ [_ id pid] ]
   {::fbe/fetch-doc (fetch-for
                     ["galleries" id "paintings" pid]
                     :painting)}))


(re-frame/reg-event-fx
 ::load-gallery
 (fn [_ [_ id]]
   {::fbe/fetch-collection (fetch-for
                            ["galleries" id "paintings"]
                            :gallery-paintings)}))


(re-frame/reg-event-fx
 ::load-recent-galleries
 (fn []
   {::fbe/fetch-collection (fetch-for ["galleries"] :recent-galleries)}))


(re-frame/reg-event-db
 ::fetch-done
 (fn [db [_ key value]]
   (assoc db key value)))


