(ns my-art-gallery.views
  (:require
   [re-frame.core :as re-frame]
   [my-art-gallery.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:div "Left Column"]
     [:div "Center"
      [:h1
       "Welcome To My Virtual Gallery" @name]
      [:button "Register as an artist" ]
      [:button "continue as a user"]
      ]

      
     ]))


