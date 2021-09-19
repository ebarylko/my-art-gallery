(ns my-art-gallery.css.styles
  (:require [garden.def :refer [defstyles]]
            [garden.color :refer [rgba]]))

(defstyles screen
  [:body
   {:background-color "white"
    :margin 0}]
  [:html :body :div#app {:height :100%}]
  [:.container
   {:background-color :green
    :width :100%
    :height :100%
    :align-items :stretch
    :display :flex
    :flex-direction :row}
   [:.register
    {:background-color :gold
     :width :200px}]
   [:.center
    {:background-color :brown
     :flex-grow 4}]
   [:.recent-galleries
    {:background-color :yellow
     :width :200px}]
   ])
