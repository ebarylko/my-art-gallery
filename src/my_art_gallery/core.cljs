(ns my-art-gallery.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [my-art-gallery.events :as events]
   [my-art-gallery.views :as views]
   [my-art-gallery.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
