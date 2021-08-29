(ns super-sample.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [my-art-gallery.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
