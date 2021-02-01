(ns musicality.compositions.2021-01-23)


(comment "unordered and ordered pitch intervals"

         ; unordered pitch interval is the distance between two pitches irrespective of time
         ; Friedmann uses ip(4)
         :4 ; :4 is already a pitch-class
         :ip4
         :ip(4)

         ; ordered pitch interval is the distance and direction between a pitch and a subsequent pitch
         ; Friedmann uses ip<+4>
         :+4
         :ip+4
         :ip<+4>

         ; unordered pitch interval mod 12
         :4%12
         :ip4%12
         :%12ip4
         :m12ip4
         :mod12ip4

         ; ordered pitch interval mod 12
         

         

)
