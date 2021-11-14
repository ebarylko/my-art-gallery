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
 ::fetch-collection
 (fn [{:keys [collection event-success event-error]}]
   (fbf/fetch-collection
    collection
    #(re-frame/dispatch-sync [event-success %])
    #(re-frame/dispatch-sync [event-error]))))



