(ns musicality.compositions.tonality-diamonds
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o]
            [musicality.osc-reaper :as rea]
            [musicality.organ-presets :as organ-presets]
            [musicality.react-presets :refer :all]))

(comment "Musicality OSC"
         (rc/connect)
         (rc/disconnect)
         (rc/print-state)
         (rc/clear-state)
         )

(comment "REAPER OSC"
         (rea/connect-local)
         (rea/disconnect)
         (rea/bpm-set 80)
         )

(defn hex-grid+ [spawner pos inst hex-grid]
  (->> hex-grid
       (map-indexed
         (fn [r q-row]
           (->> q-row
                (map-indexed
                  (fn [q val]
                    (when val
                      (tonnegg-tonnetz
                        (tonnegg-presets inst)
                        spawner
                        pos
                        q
                        r
                        (* 2 val)
                        )))))))))

(comment

  (rc/organ- 1)
  (rc/organ+
    1
    (merge (:organ1 organ-presets)
           {
            :spawnFromCam (rc/transform :pos [0 0 2] :rot [3 0 0] :sca [0.5 0.5 0.5])

            :diapason     50
            :partials     (->> (range 16)
                               (map (fn [i]
                                      {
                                       :Interval  {:Val      (+ 1 i)
                                                   :NoteType "Irrational"}
                                       :Amplitude (nth organ-presets/french-horn-a2 i)
                                       :Release   0
                                       }))
                               )}

           ))


  (def sp-tonality-diamond
    (rc/spawner+ :spawnFromCam (rc/transform :pos [0 0 1] :sca [0.5 0.5 0.5])
                 :isLocked false
                 :isParent true
                 :label "tonality diamond"))


  (def sp-tonality-diamond-2
    (rc/spawner+ :spawnFromCam (rc/transform :pos [0 0 1] :sca [0.5 0.5 0.5])
                 :isLocked false
                 :isParent true
                 :label "tonality diamond"))


  (rc/mallet- 0)
  (rc/mallet+
    :spawnerId sp-tonality-diamond
    :spawnFromSpawner (rc/transform :pos [0 0.1 -0.2] :sca [2 2 2] :rot [-10 0 0]))

  (rc/tonnegg-clear)

  (hex-grid+ sp-tonality-diamond [-0.25 1 0] :organ1
             [[_ _ 1/1]
              [_ 5/3 6/5]
              [4/3 1/1 3/2]
              [8/5 5/4]
              [1/1]])


  (hex-grid+ sp-tonality-diamond [-0.25 1 0] :organ1
             [[_ _ _ _ _ 7/7]
              [_ _ _ _ 12/7 7/6]
              [_ _ _ 11/7 3/3 14/11]
              [_ _ 10/7 11/6 12/11 7/5]
              [_ 9/7 5/3 11/11 6/5 14/9]
              [8/7 9/6 20/11 11/10 12/9 7/4]
              [4/3 18/11 5/5 11/9 3/2]
              [16/11 9/5 10/9 11/8]
              [8/5 9/9 5/4]
              [16/9 9/8]
              [1/1]])



  (hex-grid+ sp-tonality-diamond [-0.25 1 0] :organ1
             [[1/1 5/4 3/2 7/4 9/8 11/8]
              [8/5 5/5 6/5 7/5 9/5 11/10]
              [4/3 5/3 3/3 7/6 9/6 11/6]
              [8/7 10/7 12/7 7/7 9/7 11/7]
              [16/9 10/9 12/9 14/9 9/9 11/9]
              [16/11 20/11 12/11 14/11 18/11 11/11]
              ])


  (->> [[_ _ 1/1]
        [_ 5/3 6/5]
        [4/3 1/1 3/2]
        [8/5 5/4]
        [1/1]]
       (map-indexed
         (fn [r q-row]
           (->> q-row
                (map-indexed
                  (fn [q val]
                    (when val
                      (tonnegg-tonnetz
                        (tonnegg-presets :organ1)
                        sp-tonality-diamond
                        [0 1 0]
                        q
                        r
                        (* 2 val)
                        ))))))))

  (->> [[_ _ 1/1]
        [_ 5/3 6/5]
        [4/3 1/1 3/2]
        [8/5 5/4]
        [1/1]]
       (map-indexed
         (fn [r q-row]
           (->> q-row
                (map-indexed
                  (fn [q val]
                    (when val
                      (tonnegg-tonnetz
                        (tonnegg-presets :organ1)
                        sp-tonality-diamond-2
                        [0 1 0]
                        q
                        r
                        (* 4/3 4/3 4/3 val)
                        ))))))))



  ; http://lumma.org/tuning/gws/crystal.htm
  ; http://lumma.org/tuning/gws/cube.htm
  ; http://lumma.org/tuning/gws/sevlat.htm
  ; http://lumma.org/tuning/gws/commalist.htm

  )