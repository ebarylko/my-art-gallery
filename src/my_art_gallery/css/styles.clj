(ns my-art-gallery.css.styles
  (:require [garden.def :refer [defstyles]]
            [garden.color :refer [rgba]]))


(defn css-var [v]
  (format "var(--%s)" (name v)))

(defstyles screen
  [:body {:background-color (css-var :primary)
          :margin 0}]
  [:html :body :div#app {:height :100%}]

  [:.welcome {:min-height :350px
              :padding :30px
              :background (css-var :primary)}]

  [:.band-title
   [:h1 {:width :80%
         :font-size :3em
         :margin :auto
         :padding :30px
         :text-align :center}]]

  [:.users-artists {:min-height :300px
                    :background (css-var :orange)}
   [:.band-title {:color :white}]
   [:.user-options {:display :flex
                    :justify-content :space-evenly}
    [:.button {:min-width :200px
               :font-size :larger}]]]

  [:.recent-galleries {:height :auto
                       :background :white}
   [:.band-title {:color (css-var :orange)}]
   [:.cards {:flex-wrap :wrap}
    [:.card {:min-width :200px}]]])
