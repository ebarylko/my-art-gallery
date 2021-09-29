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

(defn gallery-card
  [{:keys [artist painting-url instagram avatar-url description]}]
  [:div.card
   [:div.card-image
    [:figure.image.is-4by3
     [:img {:alt "Painting",:src painting-url}]]]
   [:div.card-content
    [:div.media
     [:div.media-left
      [:figure.image.is-48x48
       [:img
        {:alt "Avatar",:src avatar-url}]]]
     [:div.media-content
      [:p.title.is-4 artist]
      [:p.subtitle.is-6 instagram]]]
    [:div.content description]]])


(def artist-info
  {:artist "Amir Barylko"
   :description "Good artist, lots of food"
   :instagram "amirbarylko"
   :painting-url "https://image.freepik.com/free-photo/oil-painting-beautiful-lotus-flower_1232-1978.jpg"
   :avatar-url "https://image.freepik.com/free-vector/mysterious-mafia-man-smoking-cigarette_52683-34828.jpg"})

(defn welcome-band [name]
  [:section.welcome
   [:div.container
     [:div.tile.is-ancestor
      [:div.tile.is-4.is-vertical.is-parent
       [:article.tile.is-child.notification.is-info
        [:p.title "Welcome"]
        [:p name]]
       [:div.tile.is-child.box
        [:p.title "Two"]
        [:p
         "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ornare magna eros, eu pellentesque tortor vestibulum ut. Maecenas non massa sem. Etiam finibus odio quis feugiat facilisis."]]]
    [:div.tile.is-parent
     [:div.tile.is-child.box
      [:p.title "Your virtual gallery at your fingertips"]
      [:p
       "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam semper diam at erat pulvinar, at pulvinar felis blandit. Vestibulum volutpat tellus diam, consequat gravida libero rhoncus ut. Morbi maximus, leo sit amet vehicula eleifend, nunc dui porta orci, quis semper odio felis ut quam."]
      ]]]]])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:section.all-bands
     [top-menu]
     [welcome-band @name]
     [:section.users-artists
      [:div.container
       [:div.band-title
        [:h1 "Artists and regular users"]]
       [:div.user-options
        [:button.button.is-warning "Log in"]
        [:button.button.is-warning "Register as an artist"]
        [:button.button.is-warning "Continue as a user"]]]]

     [:section.recent-galleries
      [:div.container
       [:div.band-title
        [:h1 "Recent galleries"]]
       [:div.cards.columns
        (for [info (repeat 10 artist-info)]
          [:div.column [gallery-card info]])
        ]]]]))


