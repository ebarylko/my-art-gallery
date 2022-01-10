(ns my-art-gallery.css.styles
  (:require [garden.def :refer [defstyles]]
            [garden.color :refer [rgba]]))


(defn css-var [v]
  (format "var(--%s)" (name v)))

(defstyles screen
  [:body {:background-color (css-var :primary)
          :margin 0}]
  [:html :body :div#app :.main-container {:height :100%}]

  [:.registration {:background (css-var :light-blue)
                   :height :100%
                   :padding :30px}
   [:h1 {:font-size :3em}]]

  [:.login {:background (css-var :pink)
            :height :100%
            :padding :30px}
   [:h1 {:font-size :3em}]]

  [:.welcome {:min-height :350px
              :padding :30px
              :background (css-var :charcoal)}]

  [:.gallery-paintings {:background (css-var :pink)
                        :height :100%
                        :display :flex
                        :padding :30px}
   [:h1 {:font-size :3em}]]

  [:.gallery-painting {:background (css-var :light-blue)
                       :padding :30px
                       :color :white}
   [:.painting
    [:header {:box-shadow :none
              :justify-content :center
              :align-items :center
              :flex-direction :column}
     [:.card-header-title {:font-size :3em}]]
    [:.card-image {:padding "1.5rem"}]
    [:.card-content {:padding-top :0px}
     [:.media {:margin-top :3em}
      [:.bio {:text-overflow :ellipsis}]
      [:figure {:max-width :200px}]]]]]


  [:.band-title
   [:h1 {:width :80%
         :font-size :3em
         :margin :auto
         :padding :30px
         :text-align :center}]]

  [:.users-artists {:min-height :300px
                    :display :flex
                    :background (css-var :orange)}
   [:.container {:margin :auto}]
   [:.band-title {:color :white}
    [:h1 {:padding-top :0px}]]
   [:.user-options {:display :flex
                    :justify-content :space-evenly}
    [:.button {:min-width :200px
               :font-size :larger}]]]

  [:.recent-galleries {:height :auto
                       :background (css-var :light-blue)
                       :padding :1.5rem}
   [:.band-title {:color :white}]
   [:.cards {:flex-wrap :wrap}
    [:.card {:min-width :200px}]]])
