(ns my-art-gallery.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [my-art-gallery.routing :as routing]
   [my-art-gallery.events :as events]
   [my-art-gallery.config :as config]
   [my-art-gallery.fb.core :as fb]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [routing/router-component {:router routing/router}] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (routing/init-routes!)
  (dev-setup)
  (mount-root)
  (fb/firebase-init!))
