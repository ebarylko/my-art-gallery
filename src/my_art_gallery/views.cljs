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

(defn top-menu []
  [:nav {:class "navbar" :role "navigation" :aria-label "main navigation"}
   [:div {:class "navbar-brand"}
    [:a {:class "navbar-item" :href "https://bulma.io"}
     [:img {:src "https://bulma.io/images/bulma-logo.png" :width "112" :height "28"}]]

    [:a {:role "button" :class "navbar-burger" :aria-label "menu"
         :aria-expanded "false" :data-target "navbarBasicExample"}
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]]]

   [:div#navbarBasicExample.navbar-menu
    [:div.navbar-start
     [:a.navbar-item "Home"]
     [:a.navbar-item "Documentation"]
     [:div.navbar-item.has-dropdown.is-hoverable
      [:a.navbar-link "More"]
      [:div.navbar-dropdown
       [:a.navbar-item "About"]
       [:a.navbar-item "Jobs"]
       [:a.navbar-item "Contact"]
       [:hr.navbar-divider]
       [:a.navbar-item "Report an issue"]]]]
    [:div.navbar-end
     [:div.navbar-item
      [:div.buttons
       [:a.button.is-primary [:strong "Sign up"]]
       [:a.button.is-light "Log in"]]]]]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:section.all-bands
     [top-menu]
     [:section.welcome 
      [:div.container
       [:div.hero.is-info.welcome-msg
        [:div.hero-body
         "Welcome To My Gallery " @name]]]]

     [:section.users-artists "Artists and regular users"]
     [:section.gallery "The gallery"]]
    #_[:section.main.container
     [:div.register [side-menu]]

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


