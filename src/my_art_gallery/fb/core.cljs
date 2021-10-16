(ns my-art-gallery.fb.core
  (:require ["firebase/app" :as Firebase]
            [my-art-gallery.fb.config :as cfg]))

(defonce firebase-instance (atom nil))

(defn init [config]
  (when-not @firebase-instance
    (reset! firebase-instance (-> Firebase (.initializeApp (clj->js config))))))

(defn firebase-init!
  []
  (init cfg/firebase))
