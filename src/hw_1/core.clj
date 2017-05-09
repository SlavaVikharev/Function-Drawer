(ns hw-1.core (:require [quil.core :as q]))

(def function #(+ (* % (q/sin (q/pow % 2))) 10))
(def interval [1 5])

(def draw-line (partial apply q/line))

(defn draw []
  (q/background 255)
  (let [[a b] interval

        step (/ (- b a) (q/width))
        xs   (range a b step)
        ys   (map function xs)

        min-y (apply min ys)
        max-y (apply max ys)
        coeff (/ (q/height) (- max-y min-y))

        mirror (partial - max-y)
        scale  (partial * coeff)
        transf (comp scale mirror)

        scaled-ys (map transf ys)
        points    (map-indexed vector scaled-ys)
        segments  (partition 2 1 points)

        x-axis (scale max-y)
        y-axis (/ (- a) step)]

    (q/line y-axis 0 y-axis (q/height))
    (q/line 0 x-axis (q/width) x-axis)
    (doall (map draw-line segments))))

(defn -main []
  (q/defsketch hw-1
    :size [800 600]
    :features [:resizable]
    :draw draw))

