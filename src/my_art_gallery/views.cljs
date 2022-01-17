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


(defn social-media-icon
  [link fa-icon]
  {:key fa-icon}
  [:a {:href link}
   [:i {:class (str "fab fa-3x " fa-icon)}]])

(def media-handles
  [{:url "https://www.instagram.com/" :field :instagram}
   {:url "https://www.snapchat.com/add/" :field :snapchat}
   {:url "https://www.twitter.com/" :field :twitter}
   {:url "https://www.facebook.com/" :field :facebook} ])


(defn display-handles
  [artist]
  [:div.social-media
   (for [{:keys [url field]} media-handles
         :when (artist field)]
     (social-media-icon (str url (artist field)) (str "fa-" (name field))))])


(defn display-error
  []
  (let [error @(re-frame/subscribe [::subs/error])]
    (when error
      [:div.container
       [:div.notification
        [:button.delete]
        "There's an error"
        [:p error]]])))

(defn gallery-content []
  (let [pts (re-frame/subscribe [::subs/gallery-paintings])
        route (re-frame/subscribe [::subs/current-route])
        gid (-> @route :path-params :id)]
    [:section.gallery-paintings
     [:div.container.box
      [:h1 "Content for gallery ("
       [:span (count @pts)]
       "paintings)"]
      [:div.paintings.columns.is-multiline
       (for [[id {:keys [name painting-url]}] @pts]
         ^{:key id}
         [:div.painting.column.is-one-quarter
          [:div.title name]
          [:a {:href (href :gallery-painting {:pid id :id gid})}
           [:figure.image.is-square
            [:img {:src painting-url}]]]])]]]))


(defn bio-text
  "displays the bio a certain way depending on the amount of characters"
  [text]
  (if (> (count text) 600)
    (str (subs text 0 600) "...")
    text))


(defn artist-profile
  "displays the profile for the artist"
  []
  (let [[artist-id artist] @(re-frame/subscribe [::subs/artist])]
        

    [:section.artist-profile
     [:div.container.is-widescreen
      [:div.title.is-1
       [:p (artist :name)]]]]))
  


(defn gallery-painting
  "This is for getting the specific painting"
  []
  (let [[id pnt] @(re-frame/subscribe [::subs/painting])
        route (re-frame/subscribe [::subs/current-route])
        gid (-> @route :path-params :id)
        [artist-id artist] @(re-frame/subscribe [::subs/artist])]
    [:section.gallery-painting
     [:div.container.is-widescreen
      [:div.card.painting
       [:header.card-header
        [:p.card-header-title (:name pnt)]
        (if artist
        [:div.title.is-5 (artist :name)])
        [:div.materials.tags 
         (for [m (get pnt :materials [])]
           ^{:key m} [:p.tag.is-info m])] ]
       [:div.card-image
        [:figure.image.painting
         [:img {:src (:painting-url pnt)}]]]
       [:div.card-content
        [:div.content (:description pnt)]
        (if artist
          [:div.media
           [:div.media-left
            [:figure.image
             [:img {:alt "Artist avatar"
                    :src (artist :avatar-url)}]]]
           [:div.media-content
            [:a {:href (href :artist {:id artist-id})}
             [:div.title.is-5 "About"]]
            [:div.bio (bio-text (artist :bio) )]]
           [:div.media-right
            [display-handles artist] ]]
          [:div "Artist info loading..."])
        ]
       [:footer.card-footer
        [:div.card-footer-item
         [:a {:href (href :galleries {:id gid})} "Back to gallery"]]]]]]))

(defn gallery-card
  [id {:keys [painting-url description] [artist-id artist-info] :artist :as glr}]
  [:div.card
   [:div.card-image
    [:figure.image.is-4by3
     [:a {:href (href :galleries {:id id})}
      [:img {:alt "Painting",:src painting-url}]]]]
   [:div.card-content
    [:div.media
     [:div.media-left
      [:figure.image.is-48x48
       [:img {:alt "Avatar",:src (:avatar-url artist-info)}]]]
     [:div.media-content
      [:p.title.is-4 (:name artist-info)]
      [:p.subtitle.is-6 (:instagram artist-info)]]]
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
     [recent-galleries-band]
     [users-band]
     [welcome-band @name]
     ]))

(defn galleries-page []
  [:section.galleries
   [:h1 "Galleries"]])

(defn login-page []
  [:section.login
   [:h1 "Log In"]])

(defn registration-page []
  [:section.registration
   [:h1 "Registration"]])
