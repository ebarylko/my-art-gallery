(ns my-art-gallery.fb.core
  (:require ["firebase/app" :as fba]
            [my-art-gallery.fb.config :as cfg]))

(defonce firebase-instance (atom nil))

(defn init [config]
  (when-not @firebase-instance
    (let [cfg (clj->js config)]
      (reset! firebase-instance (fba/initializeApp cfg)))))

(defn firebase-init!
  []
  (init cfg/firebase))
