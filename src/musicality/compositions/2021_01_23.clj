(ns musicality.compositions.2021-01-23)


(comment "unordered and ordered pitch intervals"

         ; chromatic interval http://openmusictheory.com/intervals.html
         :i1
         :i2
         :i3 

         ; unordered pitch interval is the distance between two pitches irrespective of time
         ; Friedmann uses ip(4)
         :4 ; :4 is already a pitch-class
         :ip4
         :ip(4) ; invalid
         
         ; ordered pitch interval is the distance and direction between a pitch and a subsequent pitch
         ; Friedmann uses ip<+4>
         :+4 ; I like these for concision
         :-4
         
         :ip+4
         :ip-4
         :ip<+4>
         :ip<-4>

         ; unordered pitch interval mod 12
         :4%12
         :ip4%12
         :%12ip4
         :m12ip4
         :mod12ip4

         ; ordered pitch interval mod 12
         

         
         
         ; scale degrees
         :d1 :d2 :d3 :d4

         
         :1^ ; invalid
         :^1 ;invalid
         ; all valid but only go up to 9. would be nice to use for any pc-set
         ; need Bravura to render them
         ; need minor-mode for input
         ; https://w3c.github.io/smufl/gitbook/tables/scale-degrees.html
         :
         :
         :
         :
         :
         :
         :
         :
         :    
         
         :

)
