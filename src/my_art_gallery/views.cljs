(ns my-art-gallery.views
  (:require
   [reitit.frontend.easy :as rfe]
   [re-frame.core :as re-frame]
   [my-art-gallery.subs :as subs]))

(defn href
  "Return relative url for given route. Url can be used in HTML links."
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(defn gallery-content []
  (let [pts (re-frame/subscribe [::subs/gallery-paintings])]
    [:section.gallery-paintings
     "Content for gallery "
     [:span (count @pts)]
     [:div.paintings.columns.is-multiline
      (for [[id {:keys [name paintingUrl]}] @pts]
        ^{:key id}
        [:div.painting.column.is-one-quarter
         [:div.title name]
         [:a {:href (href :gallery-painting {:pid id :id 1})}

          [:figure.image.is-square
           [:img {:src paintingUrl}]]]])]]))


(defn gallery-painting
  "this is for getting the specific painting"
  []
  [:section.gallery-painting
   [:div.container.is-widescreen
    [:div.card.painting
     [:header.card-header
      [:p.card-header-title "Flames"]]
     [:div.card-image
      [:figure.image.painting
       [:img {:src "https://images.unsplash.com/photo-1490845433441-5174fe36bc30?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1632&q=80"}]]]
     [:div.card-content
      [:div.media
       [:div.media-left
        [:figure.image.is-48x48
         [:img
          {:alt "Avatar",:src "https://images.unsplash.com/photo-1631913290783-490324506193?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80"}]]]
       [:div.media-content
        [:p.title.is-5 "Author name"]
        [:p.subtitle.is-6
         [:a {:href "https://www.instagram.com/georgiepuddingnpie"} "@fiery-heart"]]]]
      [:div.content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas faucibus in lectus eget dictum. Aenean eu elit quam. Pellentesque ultrices cursus eros, sed elementum mi imperdiet ut. Proin eu lectus ac eros pretium gravida. Sed nibh arcu, gravida bibendum lectus in, porttitor mattis eros. In eget erat at felis tincidunt faucibus. In hac habitasse platea dictumst. Praesent blandit imperdiet justo, scelerisque placerat nunc aliquam at. Integer varius, arcu eget cursus ultrices, sapien ligula maximus orci, nec rhoncus ligula odio sit amet sem. Nam a tortor at elit iaculis sollicitudin at vel augue. Etiam faucibus augue eget felis bibendum iaculis. Cras ac justo augue.flames burning in the dark night"]]
     [:footer.card-footer
      [:div.card-footer-item
       [:a {:href (href :galleries {:id 1})} "Back to gallery"]]]]]])

(defn gallery-card
  [id {:keys [artist painting-url instagram avatar-url description]}]
  [:div.card
   [:div.card-image
    [:figure.image.is-4by3
     [:a {:href (href :galleries {:id id})}
      [:img {:alt "Painting",:src painting-url}]]]]
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
       (for [[id gallery] @rgs]
           ^{:key id}
           [:div.column [gallery-card id gallery]])]]]))

(defn home-page []
  (let [name (re-frame/subscribe [::subs/name])]
    [:section.all-bands
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
