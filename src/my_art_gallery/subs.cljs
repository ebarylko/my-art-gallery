(ns my-art-gallery.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::current-route
 (fn [db]
   (:current-route db)))

(re-frame/reg-sub
 ::error
 (fn [db] (:error db)))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::recent-galleries
 (fn [db]
   (:recent-galleries db)))

(re-frame/reg-sub
 ::artist
 (fn [db]
   (get db :artist)))

(re-frame/reg-sub
 ::gallery
 (fn [db]
   (get db :gallery)))

(re-frame/reg-sub
 ::gallery-paintings
 (fn [db]
   (:gallery-paintings db)))


(re-frame/reg-sub
 ::painting
 (fn [db] (:painting db)))
