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
 ::fb-initialized
 (fn []
   {::fbe/fetch-collection
    {:collection "galleries"
     :event-success ::fetch-recent-galleries-ok
     :event-error ::fetch-recent-galleries-error}}))

(defn ->gallery [[id glr]]
  [id
   (st/rename-keys glr {:paintingUrl :painting-url
                        :createdOn :created-on
                        :avatarUrl :avatar-url})])

(re-frame/reg-event-db
 ::fetch-recent-galleries-ok
 (fn [db [_ galleries]]
   (assoc db :recent-galleries (map ->gallery galleries))))


(re-frame/reg-event-db
 ::fetch-recent-galleries-error
 (fn [db]
   (assoc db :error "Can't fetch recent galleries :(")))



