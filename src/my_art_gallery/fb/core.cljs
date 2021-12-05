(ns my-art-gallery.fb.core
  (:require ["firebase/app" :as fba]
            [re-frame.core :as re-frame]
            [my-art-gallery.fb.config :as cfg]))

(defonce firebase-instance (atom nil))

(defn init [config]
  (when-not @firebase-instance
    (let [cfg (clj->js config)]
      (reset! firebase-instance (fba/initializeApp cfg))
      (println "Firebase initialized!"))))

(defn firebase-init!
  []
  (init cfg/firebase))
