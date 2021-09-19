(ns my-art-gallery.css.styles
  (:require [garden.def :refer [defstyles]]
            [garden.color :refer [rgba]]))


(defn css-var [v]
  (format "var(--%s)" (name v)))

(defstyles screen
  [:body
   {:background-color (css-var :link)
    :margin 0}]
  [:html :body :div#app {:height :100%}]
  [:.main
   {
    :height :100%
    :align-items :stretch
    :display :flex
    :flex-direction :row}
   [:.register
    {:background-color :gold
     :width :200px}]
   [:.center
    {
     :padding :20px
     :flex-grow 4}
    [:.buttons
     [:button {:margin-right :10px}]]]
   [:.recent-galleries
    {:background-color :yellow
     :width :200px}]
   ])
