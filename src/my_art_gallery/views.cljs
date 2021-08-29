(ns my-art-gallery.views
  (:require
   [re-frame.core :as re-frame]
   [my-art-gallery.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "Hello from ahoy " @name]
     ]))
