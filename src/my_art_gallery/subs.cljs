(ns my-art-gallery.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::recent-galleries
 (fn [db]
   (:recent-galleries db)))


(re-frame/reg-sub
 ::gallery-paintings
 (fn [db]
   (:gallery-paintings db)))
