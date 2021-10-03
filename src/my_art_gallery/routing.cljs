(ns my-art-gallery.routing
  (:require
   [re-frame.core :as re-frame]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [reitit.coercion.spec :as rss]
   [reitit.core :as r]
   [reitit.frontend :as rf]
   [my-art-gallery.views :as views]))

(defn href
  "Return relative url for given route. Url can be used in HTML links."
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(def routes
  ["/"
   [""
    {:name      :home
     :view      views/home-page
     :link-text "Home"
     :controllers
     [{:start (fn [& params](js/console.log "Entering home page"))
       :stop  (fn [& params] (js/console.log "Leaving home page"))}]}]
   ["registration"
    {:name      :registration
     :view      views/registration-page
     :link-text "Sign up"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering regs"))
       :stop  (fn [& params] (js/console.log "Leaving regs"))}]}]
   ["login"
    {:name      :login
     :view      views/login-page
     :link-text "Login"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering Login"))
       :stop  (fn [& params] (js/console.log "Leaving Login"))}]}]])

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
    [:a {:class "navbar-item" :href "https://bulma.io"}
     [:img {:src "https://bulma.io/images/bulma-logo.png" :width "112" :height "28"}]]

    [:a {:role "button" :class "navbar-burger" :aria-label "menu"
         :aria-expanded "false" :data-target "mvgTopMenu"}
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]]]

   [:div#mvgTopMenu.navbar-menu
    [:div.navbar-start
     [:a.navbar-item {:href (href :home)
                      :class (when (= :home (-> current-route :data :name)) "is-selected")}
      "Home"]]
    [:div.navbar-end
     [:div.navbar-item
      [:div.buttons
       [:a.button.is-primary {:href (href :registration)}
        [:strong "Sign up"]]
       [:a.button.is-light {:href (href :login)}
        "Log in"]]]]]])

(defn router-component [{:keys [router]}]
  (let [current-route @(re-frame/subscribe [:current-route])]
    [:div.main-container
     [top-menu {:router router :current-route current-route}]
     (when current-route
       [(-> current-route :data :view)])]))


