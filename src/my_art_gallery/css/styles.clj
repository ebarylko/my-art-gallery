(ns my-art-gallery.css.styles
  (:require [garden.def :refer [defstyles]]
            [garden.color :refer [rgba]]))

(defstyles screen
  [:body
   {:background-color "white"
    :margin 0}]
  [:html :body :div#app {:height :100%}]
  [:.main
   {:background-color :green
    :height :100%
    :align-items :stretch
    :display :flex
    :flex-direction :row}
   [:.register
    {:background-color :gold
     :width :200px}]
   [:.center
    {:background-color :white
     :padding :20px
     :flex-grow 4}
    [:.buttons
     [:button {:margin-right :10px}]]]
   [:.recent-galleries
    {:background-color :yellow
     :width :200px}]
   ])
