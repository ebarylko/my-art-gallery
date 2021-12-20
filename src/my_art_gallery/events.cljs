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
  {:path (cond->> path
           (vector? path) (clojure.string/join "/"))
   :success (cond->> key
              (keyword? key) (vector ::fetch-done))
   :error [::fetch-done :error]})


(re-frame/reg-event-fx
 ::load-painting
 (fn [_ [_ id pid] ]
   {:fx [[::fbe/fetch-doc (fetch-for
                           ["galleries" id "paintings" pid]
                           :painting)]

         [::fbe/fetch-doc (fetch-for
                           ["galleries" id]
                           [::load-artist])]]}))


(re-frame/reg-event-fx
 ::load-artist
 (fn [{:keys [db]} [_ [id {:keys [artist-ref] :as glr}]]]
   (cond-> {:db (assoc db :gallery glr)}
     artist-ref (assoc ::fbe/fetch-doc-ref
                       (fetch-for artist-ref :artist)))))


(re-frame/reg-event-fx
 ::load-gallery
 (fn [_ [_ id]]
   {::fbe/fetch-collection (fetch-for
                            ["galleries" id "paintings"]
                            :gallery-paintings)}))


(re-frame/reg-event-fx
 ::load-recent-galleries
 (fn []
   {::fbe/fetch-collection {:path "galleries"
                            :success [::load-artists]
                            :error [::fetch-done :error]}}))


(re-frame/reg-event-fx
 ::load-artists
 (fn [cofx [_ galleries]]
   (doseq [[id glr] galleries :when (:artist-ref glr)]
     (fbe/fetch fbf/fetch-doc-ref
                {:path (:artist-ref glr)
                 :success [::fetch-done [:recent-galleries id :artist]]
                 :error [::fetch-done :error]}))
   {:db (assoc (:db cofx) :recent-galleries (into {} galleries))}))


(re-frame/reg-event-db
 ::fetch-done
 (fn [db [_ key value]]
   (if (coll? key)
     (assoc-in db key value)
     (assoc db key value))))

