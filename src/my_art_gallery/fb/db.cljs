(ns my-art-gallery.fb.db
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [reagent.ratom :as ratom]
            [cljs-bean.core :refer [->js ->clj]]
            [clojure.string :as string]))

(defn ->path [p]
  (string/join "/" (->js p)))

(def store nil)

(defn database-ref [path]
  (-> (.database ^js store)
      (.ref (->path path))))

(rf/reg-event-fx
  ::firebase-error
  (fn [_ [_ error]]
    (js/console.error (str "error:\n" error))))

(rf/reg-event-fx
  ::firebase-success
  (fn [_ [_]]
    (js/console.log (str "Write Succeeded"))))

(def default-pass-fail
  {:on-success [::firebase-success]
   :on-failure [::firebase-error]})


(defn success-failure-dispatch [args]
  (let [{:keys [on-success on-failure]} (merge default-pass-fail args)]
    (fn [err]
      (rf/dispatch
        (if (nil? err)
          on-success
          on-failure)))))


(defn on-value-reaction
  "returns a reagent atom that will always have the latest value at 'path' in the Firebase database"
  [{:keys [path] :as args}]
  (let [ref ^js (database-ref path)
        reaction (r/atom nil)
        callback (fn [^js x] (reset! reaction (some-> x (.val) ->clj)))]
    (.on ref "value" callback (success-failure-dispatch args))
    (ratom/make-reaction
      (fn [] @reaction)
      :on-dispose #(do (.off ref "value" callback)))))

(rf/reg-sub ::firestore-value
  (fn [[_ args]]
    (on-value-reaction args))
  identity)
