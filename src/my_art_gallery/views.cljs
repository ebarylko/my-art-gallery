(ns my-art-gallery.views
  (:require
   [re-frame.core :as re-frame]
   [my-art-gallery.subs :as subs]))


(defn side-menu []
  [:side.menu
   [:p.menu-label "General"]
   [:ul.menu-list
    [:li [:a "What can users do?"]]
    [:li [:a "What can artists do?"]]]])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:section.main.container

     [:div.register 
      [side-menu]]

     [:div.center 
      [:section.hero.is-primary
       [:div.hero-body
        "Welcome To My Gallery " @name]]

      [:section.section.buttons
       [:button.button.is-info "Register as an artist" ]
       [:button.button.is-warning "Continue as a user"]]]

     [:div.recent-galleries
      [:div.gallery "Gallery 1"]
      [:div.gallery "Gallery 2"]]
     ]))


