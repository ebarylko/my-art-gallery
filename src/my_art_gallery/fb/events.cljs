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
 (fn [{:keys [path success error]}]
   (fbf/fetch-collection path
                         #(re-frame/dispatch (conj success %))
                         #(re-frame/dispatch (conj error %)))))


(re-frame/reg-fx
 ::fetch-doc
 (fn [{:keys [path success error]}]
   (fbf/fetch-doc path
                  #(re-frame/dispatch (conj success %))
                  #(re-frame/dispatch (conj error %)))))


