(ns my-art-gallery.routing
  (:require
   [re-frame.core :as re-frame]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [reitit.coercion.spec :as rss]
   [reitit.core :as r]
   [reitit.frontend :as rf]
   [my-art-gallery.events :as events]
   [my-art-gallery.views :as views]))

(def routes
  ["/"
   ["" {:name      :home
        :view      views/home-page
        :link-text "Home"
        :controllers [{:start (fn [& params]
                                (re-frame/dispatch [::events/load-recent-galleries]))}]}]

   ["registration" {:name      :registration
                    :view      views/registration-page
                    :link-text "Sign up"}]

   ["login" {:name      :login
             :view      views/login-page
             :link-text "Login"}]

   ["artists"
    ["/:id" {:name :artist
             :view views/artist-profile
             :controllers [{:parameters {:path [:id]}
                            :start (fn [params]
                                     (let [gid (-> params :path :id)]
                                       (re-frame/dispatch [::events/load-gallery gid])))}]}]]

   ["galleries/:id"
    ["" {:name      :galleries
         :view      views/gallery-content
         :controllers [{:parameters {:path [:id]}
                        :start (fn [params]
                                 (let [gid (-> params :path :id)]
                                   (re-frame/dispatch [::events/load-gallery gid])))}]}]

    ["/paintings/:pid" {:name :gallery-painting
                        :view views/gallery-painting
                        :controllers [{:parameters {:path [:id :pid]}
                                       :start (fn [params]
                                                (let [gid (-> params :path :id)
                                                      pid (-> params :path :pid)]
                                                  (re-frame/dispatch [::events/load-painting gid pid])))}]}]]])


(defn on-navigate [new-match]
  (when new-match
    :current-route
    (re-frame/dispatch [:navigated new-match])))

;; events

(re-frame/reg-event-fx
 :push-state
 (fn [_ [_ & route]]
   {:push-state route}))

(re-frame/reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))


(re-frame/reg-fx
 :push-state
 (fn [route]
   (apply rfe/push-state route)))

;; subscriptions
(re-frame/reg-sub
 :current-route
 (fn [db]
   (:current-route db)))

(def router
  (rf/router
    routes
    {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
    router
    on-navigate
    {:use-fragment true}))

;; views
(defn top-menu [{:keys [router current-route]}]
  [:nav {:class "navbar" :role "navigation" :aria-label "main navigation"}
   [:div {:class "navbar-brand"}
    [:a {:class "navbar-item" :href (views/href :home)}
     [:img {:src "/img/logo.png" }]]

    [:a {:role "button" :class "navbar-burger" :aria-label "menu"
         :aria-expanded "false" :data-target "mvgTopMenu"}
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]]]

   [:div#mvgTopMenu.navbar-menu
    [:div.navbar-start
     [:a.navbar-item {:href (views/href :home)
                      :class (when (= :home (-> current-route :data :name)) "is-selected")}
      "Home"]]
    [:div.navbar-end
     [:div.navbar-item
      [:div.buttons
       [:a.button.is-primary {:href (views/href :registration)}
        [:strong "Sign up"]]
       [:a.button.is-light {:href (views/href :login)}
        "Log in"]]]]]])




(defn router-component [{:keys [router]}]
  (let [current-route @(re-frame/subscribe [:current-route])]
    [:div.main-container
     [top-menu {:router router :current-route current-route}]
     [views/display-error]
     (when current-route
       [(-> current-route :data :view)])]))


