(ns musicality.compositions.2021-07-11)


(defn parse-keyword [kw] 
  (condp
      (fn parse-type [regex keyword]
        (not (nil? (re-matches regex (name keyword)))))
      kw

                                        ;  just use ints 01 instead of :01                                        
                                        ;  #"\d\d+"
                                        ;  "12ET pitch"

    #"[0123456789૪Ɛ]"
    {:type :12ET-pc
     :data  kw}

    #"[\+-]\d+"
    {:type :12ET-interval
     :data  (read-string (name kw))}

    #"\d+:\d+"
    {:type :JI-interval
     :data  (read-string 
             (clojure.string/replace (name kw) #":" "/"))}

    #"\d+\|\d+"
    {:type :JI-pc
     :data  (read-string 
             (clojure.string/replace (name kw) #"\|" "/"))}))


#_(->> [:0 :૪ :+5 :-7 :3:2 :111:64 :5|4 :111|64]
     (map (fn [kw] (str kw " - " (parse-keyword kw)))))


(defn ratio->JI-interval [r]
  (keyword (clojure.string/replace 
            (if (ratio? r)
              (str r)
              (str r "/1"))
            #"/" ":") ))


#_(ratio->JI-interval 1/2)
#_(ratio->JI-interval 2)

(defn +JI-interval [a b] 
  (ratio->JI-interval
   (* (:data (parse-keyword a)) (:data (parse-keyword b)))) )

#_(+JI-interval :3:2 :9:8)

(defn -JI-interval [a b] 
  (ratio->JI-interval
   (/ (:data (parse-keyword a)) (:data (parse-keyword b)))) )

 #_(-JI-interval :3:2 :9:8)
 #_(-JI-interval :5:4 :11:8)

#_(+JI-interval :11:10 :5:4)

(defn complement-JI-interval [a]
  (-JI-interval :2:1 a))

#_(complement-JI-interval :3:2)

(+JI-interval :5:4 :4:3)



(defn div-JI-interval [a div]
  (let [r (:data (parse-keyword a))
        n (* (if (ratio? r)
               (numerator r)
               r)
             div)
        d (* (if (ratio? r)
               (denominator r)
               1)
             div)
        ns (reverse (range d (+ 1 n)))
        pairs (map vector ns (drop 1 ns))]
    (->> pairs
         (map (fn [[a b]] (keyword (str a ":" b))))
         (set)
         )
    ))


; Doty p27
; superparticular x+1:x ratios can be divided into n parts
#_(div-JI-interval :2:1 2)
#_(div-JI-interval :2:1 3)
#_(div-JI-interval :2:1 4)
#_(div-JI-interval :2:1 10)

; for non-superparticular ratios, n is determined by the ratio and the divisor when >1, adds some number of divisions
#_(div-JI-interval :5:3 1)
#_(div-JI-interval :5:3 2)

; TODO: restrict to desired prime limit
#_(div-JI-interval :2:1 5)

; TODO: how can we get 2 equal divisions of 4:3
#_(div-JI-interval :16:9 1)

#_(defn +
  "When "
  ([] 0)
  ([x] (cast Number x))
  ([x y] ())
  ([x y & more]
     (reduce + (+ x y) more)))

(comment
  (defn + [a b] (+ a b))
  (+ 3 4)
  (ns-unmap *ns* '+)


  (type #{}))

(comment
  (fn [v] (let [v-type (class (first v))] (if (every? #(instance? v-type %) v) v-type :default)))
  13 :53
  user=> (defn q [v] (let [v-type (class (first v))] (if (every? #(instance? v-type %) v) v-type :default)))
  #'user/q
  user=> (q [1 2 3 4])
  java.lang.Long
  user=> (q ["a" "b" "c"])
  java.lang.String
  user=> (q [:a :b :c])
  clojure.lang.Keyword
  user=> (q [1 :two 3])
  :default
  user=>)
