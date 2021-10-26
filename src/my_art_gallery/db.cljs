(ns my-art-gallery.db)


(defn artist-info [id]
  {:id id
   :artist "Some Artist"
   :description "Good artist, lots of food"
   :instagram "amirbarylko"
   :painting-url "https://image.freepik.com/free-photo/oil-painting-beautiful-lotus-flower_1232-1978.jpg"
   :avatar-url "https://image.freepik.com/free-vector/mysterious-mafia-man-smoking-cigarette_52683-34828.jpg"})


(def default-db
  {:name "Eitan's gallery"
   :current-route nil
   :recent-galleries (for [i (range 10)] [i (artist-info i)])})

