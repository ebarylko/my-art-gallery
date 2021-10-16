(ns my-art-gallery.views
  (:require
   [re-frame.core :as re-frame]
   [my-art-gallery.fb.firestore :as fdb]
   [my-art-gallery.fb.events :as fev]
   [my-art-gallery.subs :as subs]))

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

(defn users-band []
  [:section.users-artists
   [:div.container
    [:div.band-title
     [:h1 "Artists and regular users"]]
    [:div.user-options
     [:button.button.is-warning "Log in"]
     [:button.button.is-warning "Register as an artist"]
     [:button.button.is-warning "Continue as a user"]]]])

(defn recent-galleries-band []
  (let [rgs (re-frame/subscribe [::subs/recent-galleries])]
    [:section.recent-galleries
     [:div.container
      [:div.band-title
       [:h1 "Recent galleries"]]
      [:div.cards.columns
       (for [gallery @rgs]
         ^{:key (:id gallery)}
         [:div.column [gallery-card gallery]])]]]))

(defn home-page []
  (let [name (re-frame/subscribe [::subs/name])]
    [:section.all-bands
     [:div
      [:button.button {:on-click #(fdb/get-galleries)} "Hey"]]
     [welcome-band @name]
     [users-band]
     [recent-galleries-band]]))

(defn galleries-page []
  [:section.galleries
   [:h1 "Galleries"]])

(defn login-page []
  [:section.login
   [:h1 "Log In"]])

(defn registration-page []
  [:section.registration
   [:h1 "Registration"]])
