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

(re-frame/reg-event-fx
 ::load-painting
 (fn [_ [_ id pid] ]
   {::fbe/fetch-doc {:path (str "galleries/" id "/paintings/" pid)
                     :event-success ::load-doc-ok
                     :event-error ::load-doc-error}}))

(re-frame/reg-event-fx
 ::load-gallery
 (fn [_ [_ id]]
   {::fbe/fetch-collection
    {:collection (str "galleries/" id "/paintings")
     :event-success ::load-gallery-ok
     :event-error ::load-gallery-error}}))

(re-frame/reg-event-db
 ::load-gallery-ok
 (fn [db [_ galleries]]
   (assoc db :gallery-paintings galleries)))


(re-frame/reg-event-db
 ::load-gallery-error
 (fn [db]
   (assoc db :error "Can't load gallery :(")))

(re-frame/reg-event-fx
 ::load-recent-galleries
 (fn []
   {::fbe/fetch-collection {:collection "galleries"
                            :event-success ::fetch-recent-galleries-ok
                            :event-error ::fetch-recent-galleries-error}}))

(re-frame/reg-event-db
 ::fetch-recent-galleries-ok
 (fn [db [_ galleries]]
   (assoc db :recent-galleries galleries)))



(re-frame/reg-event-db
 ::fetch-recent-galleries-error
 (fn [db]
   (assoc db :error "Can't fetch recent galleries :(")))

(defn ->painting [[id pnt]]
  [id
   (st/rename-keys pnt {:paintingUrl :painting-url})])

(re-frame/reg-event-db
 ::load-doc-ok
 (fn [db [_ painting]]
   (assoc db :painting (->painting painting))))

(re-frame/reg-event-db
 ::load-doc-error
 (fn [db]
   (assoc db :error "Can't fetch painting :(")))
