(defproject my-art-gallery "0.1.0-SNAPSHOT"
  :description "My virtual art gallery"

  :dependencies [[thheller/shadow-cljs "2.15.10"]
                 [reagent "1.1.0"]
                 [re-frame "1.2.0"]
                 [day8.re-frame/tracing "0.6.2"]
                 [re-com "2.13.2"]
                 [cljs-bean/cljs-bean "1.5.0"]
                 [metosin/reitit "0.5.15"]
                 [clj-commons/pushy "0.3.10"]
                 [garden "1.3.10"]
                 [garden-gnome "0.1.0"]
                 [net.dhleong/spade "1.1.0"]
                 [binaryage/devtools "1.0.3"]
                 [day8.re-frame/re-frame-10x "1.1.11"]]

  :min-lein-version "2.6.1"

  :source-paths ["src" "test"]
  :test-paths ["test"]
  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js" "node_modules"]

  :uberjar-name "my_art_gallery.jar"
  :main my-art-gallery.main

  :aliases {"js-watch" ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app"]
            "js-build" ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]}

  :profiles {:dev     {:repl-options {:init-ns user}
                       :source-paths ["dev"]
                       :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                      [garden-gnome "0.1.0"]]}

             :uberjar {:aot          :all
                       :omit-source  true
                       :dependencies [[garden-gnome "0.1.0"]]
                       :prep-tasks   ["compile" "js-build" "css-build"]}})
