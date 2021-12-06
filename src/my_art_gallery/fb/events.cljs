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


(defn fetch
  [f {:keys [path success error]}]
  (f path
     #(re-frame/dispatch (conj success %))
     #(re-frame/dispatch (conj error %))))


(re-frame/reg-fx
 ::fetch-collection
 (partial fetch fbf/fetch-collection))


(re-frame/reg-fx
 ::fetch-doc-ref
 (partial fetch fbf/fetch-doc-ref))


(re-frame/reg-fx
 ::fetch-doc
 (partial fetch fbf/fetch-doc))


