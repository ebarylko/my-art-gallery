(ns my-art-gallery.fb.events
  (:require [my-art-gallery.fb.auth :as firebase-auth]
            [my-art-gallery.fb.firestore :as fbf]
            [re-frame.core :as re-frame]))

(def interceptors [re-frame/trim-v])

(re-frame/reg-fx
 ::email-login!
 (fn [args]
   (firebase-auth/email-login args)))

(re-frame/reg-event-fx
 ::email-login
 interceptors
 (fn [_ [args]]
   {::email-login! args}))

(re-frame/reg-fx
 ::query
 (fn [{:keys [:query :on-success :on-error]}]
   (-> query
       (.then #(on-success %))
       (.catch #(on-error %)))))


(re-frame/reg-event-db
 ::fetch-recent-galleries-ok
 (fn [db [_ galleries]]
   (println galleries)
   (assoc db :recent-galleries galleries)))


(re-frame/reg-event-db
 ::fetch-recent-galleries-error
 (fn [db]
   (assoc db :error "Can't fetch recent galleries :(")))


(defn fetch-recent-galleries []
  (fbf/get-galleries
   ::fetch-recent-galleries-ok
   ::fetch-recent-galleries-error))

