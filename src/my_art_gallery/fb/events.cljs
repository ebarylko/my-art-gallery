(ns my-art-gallery.fb.events
  (:require [my-art-gallery.fb.auth :as firebase-auth]
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
