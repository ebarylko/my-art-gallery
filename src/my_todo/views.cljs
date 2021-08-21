(ns my-todo.views
  (:require
   [re-frame.core :as re-frame]
   [my-todo.subs :as subs]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "Hello " @name]
     ]))
