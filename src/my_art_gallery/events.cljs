(ns my-art-gallery.events
  (:require
   [re-frame.core :as re-frame]
   [my-art-gallery.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


