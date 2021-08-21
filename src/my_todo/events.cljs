(ns my-todo.events
  (:require
   [re-frame.core :as re-frame]
   [my-todo.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
