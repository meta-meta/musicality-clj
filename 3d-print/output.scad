union () {
  translate ([0, 0, 0.675]) {
    union () {
      translate ([0, 0, 0.4]) {
        color ([0, 0, 0, 1]) {
          difference () {
            cylinder ($fn=144, h=0.2, r=33.6);
            cylinder ($fn=144, h=10, r=33.199999999999996, center=true);
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([0.0, 17.0, 0]) {
          rotate ([0.0,0.0,-0.0]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("-0", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([14.722431864335459, -8.499999999999996, 0]) {
          rotate ([0.0,0.0,-119.99999999999999]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("-8", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([-8.499999999999995, -14.72243186433546, 0]) {
          rotate ([0.0,0.0,-209.99999999999997]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("-5", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([-14.722431864335464, 8.49999999999999, 0]) {
          rotate ([0.0,0.0,-299.99999999999994]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("-2", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([0.0, 30.88, 0]) {
          rotate ([0.0,0.0,-0.0]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("+0", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([26.742864468863466, -15.439999999999992, 0]) {
          rotate ([0.0,0.0,-119.99999999999999]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("+4", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([-15.43999999999999, -26.74286446886347, 0]) {
          rotate ([0.0,0.0,-209.99999999999997]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("+7", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      translate ([0, 0, 0.4]) {
        translate ([-26.742864468863477, 15.439999999999978, 0]) {
          rotate ([0.0,0.0,-299.99999999999994]) {
            color ([0, 0, 0, 1]) {
              linear_extrude (height=0.2){
                text ("+10", size=2.72, font="JetBrains Mono", halign="center", valign="center");
              }
            }
          }
        }
      }
      union () {
        difference () {
          difference () {
            color ([1, 1, 1, 0.5]) {
              cylinder ($fn=144, h=0.4, r=33.6);
            }
            cylinder ($fn=144, h=1, r=12, center=true);
          }
          linear_extrude (height=2, center=true){
            polygon (points=[[-6.419747506037398, 26.740172810936105], [-5.364983855443527, 26.971595211088836], [-4.301947788606349, 27.16142936636629], [-3.232278430090535, 27.309382566260474], [-2.157625132515736, 27.415226677661018], [-1.0796449333743867, 27.47879849661988], [0.0, 27.5], [1.0796449333743867, 27.47879849661988], [2.157625132515736, 27.415226677661018], [3.232278430090535, 27.309382566260474], [4.301947788606349, 27.16142936636629], [5.364983855443527, 26.971595211088836], [6.419747506037398, 26.740172810936105], [4.7856299590460605, 19.93358336815237], [3.999351601330629, 20.106098248266225], [3.206906533324733, 20.247610982200325], [2.4095166478856718, 20.35790336757599], [1.6084114624208214, 20.436805341529123], [0.8048262230609065, 20.48419524293482], [0.0, 20.5], [-0.8048262230609065, 20.48419524293482], [-1.6084114624208214, 20.436805341529123], [-2.4095166478856718, 20.35790336757599], [-3.206906533324733, 20.247610982200325], [-3.999351601330629, 20.106098248266225], [-4.7856299590460605, 19.93358336815237], [-6.419747506037398, 26.740172810936105]]);
          }
          linear_extrude (height=2, center=true){
            polygon (points=[[26.367542708875312, -7.810421979357867], [26.040578561115407, -8.839585295836937], [25.67346172867305, -9.855118612495751], [25.266758279094706, -10.855456050567344], [24.821095319621172, -11.83905516222811], [24.33716003023365, -12.804399308940564], [23.815698604072065, -13.749999999999995], [23.257515096859272, -14.674399187679306], [22.663470187105435, -15.576171515432897], [22.034479849004168, -16.453926515693123], [21.371513940066702, -17.306310753870527], [20.675594705671887, -18.132009915251885], [19.947795202837913, -18.92975083157823], [14.870174605751899, -14.111268801721954], [15.412716053319043, -13.516589209551405], [15.931492209867907, -12.901068016521664], [16.425703160166744, -12.26565431169851], [16.894586866751325, -11.611327856959068], [17.33742034493146, -10.93909757627003], [17.753520777580995, -10.249999999999995], [18.14224656799236, -9.545097666664784], [18.502998329172144, -8.825477484570046], [18.83521980805242, -8.092249055877474], [19.13839874319264, -7.346542965678651], [19.412067654649668, -6.589509038714808], [19.65580456479796, -5.82231456643041], [26.367542708875312, -7.810421979357867]]);
          }
          linear_extrude (height=2, center=true){
            polygon (points=[[-7.810421979357865, -26.367542708875312], [-8.839585295836942, -26.040578561115407], [-9.85511861249575, -25.67346172867305], [-10.855456050567337, -25.26675827909471], [-11.83905516222811, -24.821095319621172], [-12.804399308940562, -24.33716003023365], [-13.749999999999993, -23.815698604072068], [-14.674399187679304, -23.257515096859272], [-15.576171515432897, -22.663470187105435], [-16.45392651569312, -22.034479849004168], [-17.306310753870527, -21.371513940066706], [-18.132009915251885, -20.675594705671887], [-18.92975083157823, -19.947795202837916], [-14.111268801721954, -14.8701746057519], [-13.516589209551405, -15.412716053319043], [-12.901068016521664, -15.931492209867908], [-12.265654311698508, -16.425703160166744], [-11.611327856959068, -16.894586866751325], [-10.939097576270026, -17.33742034493146], [-10.249999999999995, -17.753520777580995], [-9.545097666664782, -18.14224656799236], [-8.825477484570046, -18.502998329172144], [-8.092249055877469, -18.83521980805242], [-7.346542965678649, -19.13839874319264], [-6.589509038714811, -19.412067654649668], [-5.822314566430409, -19.65580456479796], [-7.810421979357865, -26.367542708875312]]);
          }
          linear_extrude (height=2, center=true){
            polygon (points=[[-26.367542708875312, 7.810421979357864], [-26.040578561115417, 8.839585295836917], [-25.673461728673058, 9.855118612495737], [-25.26675827909471, 10.855456050567335], [-24.821095319621183, 11.839055162228085], [-24.337160030233665, 12.804399308940539], [-23.815698604072075, 13.749999999999982], [-23.257515096859272, 14.674399187679304], [-22.663470187105435, 15.576171515432893], [-22.03447984900418, 16.453926515693098], [-21.371513940066713, 17.306310753870513], [-20.675594705671887, 18.13200991525188], [-19.947795202837934, 18.929750831578207], [-14.870174605751915, 14.111268801721938], [-15.412716053319043, 13.516589209551402], [-15.931492209867912, 12.901068016521656], [-16.425703160166755, 12.265654311698492], [-16.894586866751325, 11.611327856959067], [-17.33742034493146, 10.939097576270026], [-17.753520777581, 10.249999999999986], [-18.142246567992366, 9.545097666664766], [-18.502998329172154, 8.825477484570028], [-18.83521980805242, 8.092249055877469], [-19.138398743192642, 7.3465429656786405], [-19.41206765464967, 6.589509038714793], [-19.65580456479796, 5.822314566430407], [-26.367542708875312, 7.810421979357864]]);
          }
        }
        color ([0, 0, 0, 1]) {
          translate ([0, 0, 0.4]) {
            linear_extrude (height=0.2){
              polygon (points=[[-6.419747506037398, 26.740172810936105], [-4.7856299590460605, 19.93358336815237], [-3.999351601330629, 20.106098248266225], [-3.206906533324733, 20.247610982200325], [-2.4095166478856718, 20.35790336757599], [-1.6084114624208214, 20.436805341529123], [-0.8048262230609065, 20.48419524293482], [0.0, 20.5], [0.8048262230609065, 20.48419524293482], [1.6084114624208214, 20.436805341529123], [2.4095166478856718, 20.35790336757599], [3.206906533324733, 20.247610982200325], [3.999351601330629, 20.106098248266225], [4.7856299590460605, 19.93358336815237], [6.419747506037398, 26.740172810936105], [5.364983855443527, 26.971595211088836], [4.301947788606349, 27.16142936636629], [3.232278430090535, 27.309382566260474], [2.157625132515736, 27.415226677661018], [1.0796449333743867, 27.47879849661988], [0.0, 27.5], [-1.0796449333743867, 27.47879849661988], [-2.157625132515736, 27.415226677661018], [-3.232278430090535, 27.309382566260474], [-4.301947788606349, 27.16142936636629], [-5.364983855443527, 26.971595211088836], [-6.419747506037398, 26.740172810936105], [-6.843061546206005, 26.944619289847772], [-5.720606457244129, 27.205048460926086], [-4.588323442926837, 27.418739722735033], [-3.4481577503234915, 27.585325956545883], [-2.30206816883928, 27.70452096943777], [-1.1520236650412325, 27.776119985973292], [0.0, 27.8], [1.152023665041234, 27.776119985973292], [2.302068168839281, 27.70452096943777], [3.4481577503234915, 27.585325956545883], [4.588323442926839, 27.418739722735033], [5.72060645724413, 27.205048460926086], [6.843061546206005, 26.944619289847772], [4.972296519185658, 19.57846437607644], [4.156699655983144, 19.767697083118954], [3.3339616383856887, 19.922969151052076], [2.505495919299803, 20.044013824540535], [1.6727257917465277, 20.13062315045478], [0.837081943663055, 20.18264833513167], [0.0, 20.2], [-0.8370819436630539, 20.18264833513167], [-1.672725791746527, 20.13062315045478], [-2.505495919299803, 20.044013824540535], [-3.3339616383856874, 19.922969151052076], [-4.156699655983144, 19.767697083118954], [-4.972296519185658, 19.57846437607644], [-6.843061546206005, 26.944619289847772]]);
            }
          }
        }
        color ([0, 0, 0, 1]) {
          translate ([0, 0, 0.4]) {
            linear_extrude (height=0.2){
              polygon (points=[[26.367542708875312, -7.810421979357867], [19.65580456479796, -5.82231456643041], [19.412067654649668, -6.589509038714808], [19.13839874319264, -7.346542965678651], [18.83521980805242, -8.092249055877474], [18.502998329172144, -8.825477484570046], [18.14224656799236, -9.545097666664784], [17.753520777580995, -10.249999999999995], [17.33742034493146, -10.93909757627003], [16.894586866751325, -11.611327856959068], [16.425703160166744, -12.26565431169851], [15.931492209867907, -12.901068016521664], [15.412716053319043, -13.516589209551405], [14.870174605751899, -14.111268801721954], [19.947795202837913, -18.92975083157823], [20.675594705671887, -18.132009915251885], [21.371513940066702, -17.306310753870527], [22.034479849004168, -16.453926515693123], [22.663470187105435, -15.576171515432897], [23.257515096859272, -14.674399187679306], [23.815698604072065, -13.749999999999995], [24.33716003023365, -12.804399308940564], [24.821095319621172, -11.83905516222811], [25.266758279094706, -10.855456050567344], [25.67346172867305, -9.855118612495751], [26.040578561115407, -8.839585295836937], [26.367542708875312, -7.810421979357867], [26.756255573411398, -7.546044506249057], [26.4205663069708, -8.648333713436323], [26.039486861105456, -9.735765199013192], [25.613671925204752, -10.80647077023659], [25.143853043631434, -11.858610969260532], [24.630837358938155, -12.890378233300076], [24.0755062252074, -13.899999999999995], [23.47881369389692, -14.885741752673207], [22.84178487479215, -15.845910000177225], [22.165514174881267, -16.778855186309276], [21.451163418178623, -17.682974523721832], [20.699959849726675, -18.556714747489753], [19.913194027205392, -19.398574783598704], [14.469299257178017, -14.095367288801935], [15.040978020304992, -13.483656039542913], [15.586816584431947, -12.848780049610827], [16.10587720620869, -12.191830027462135], [16.597268146431706, -11.513934604445321], [17.06014520204021, -10.816258395827292], [17.49371315644566, -10.099999999999994], [17.897227145703265, -9.366389939304371], [18.269993938178235, -8.616688546009451], [18.61137312550849, -7.852183797078386], [18.92077822281763, -7.07418910144124], [19.197677676288137, -6.284041043576033], [19.441595776363677, -5.483097087274494], [26.756255573411398, -7.546044506249057]]);
            }
          }
        }
        color ([0, 0, 0, 1]) {
          translate ([0, 0, 0.4]) {
            linear_extrude (height=0.2){
              polygon (points=[[-7.810421979357865, -26.367542708875312], [-5.822314566430409, -19.65580456479796], [-6.589509038714811, -19.412067654649668], [-7.346542965678649, -19.13839874319264], [-8.092249055877469, -18.83521980805242], [-8.825477484570046, -18.502998329172144], [-9.545097666664782, -18.14224656799236], [-10.249999999999995, -17.753520777580995], [-10.939097576270026, -17.33742034493146], [-11.611327856959068, -16.894586866751325], [-12.265654311698508, -16.425703160166744], [-12.901068016521664, -15.931492209867908], [-13.516589209551405, -15.412716053319043], [-14.111268801721954, -14.8701746057519], [-18.92975083157823, -19.947795202837916], [-18.132009915251885, -20.675594705671887], [-17.306310753870527, -21.371513940066706], [-16.45392651569312, -22.034479849004168], [-15.576171515432897, -22.663470187105435], [-14.674399187679304, -23.257515096859272], [-13.749999999999993, -23.815698604072068], [-12.804399308940562, -24.33716003023365], [-11.83905516222811, -24.821095319621172], [-10.855456050567337, -25.26675827909471], [-9.85511861249575, -25.67346172867305], [-8.839585295836942, -26.040578561115407], [-7.810421979357865, -26.367542708875312], [-7.546044506249055, -26.756255573411398], [-8.64833371343632, -26.4205663069708], [-9.73576519901319, -26.039486861105456], [-10.806470770236595, -25.613671925204752], [-11.85861096926053, -25.143853043631438], [-12.890378233300074, -24.63083735893816], [-13.899999999999993, -24.0755062252074], [-14.885741752673207, -23.47881369389692], [-15.845910000177222, -22.84178487479215], [-16.778855186309272, -22.165514174881267], [-17.68297452372183, -21.451163418178623], [-18.55671474748975, -20.699959849726675], [-19.398574783598704, -19.913194027205392], [-14.095367288801935, -14.469299257178017], [-13.483656039542911, -15.040978020304994], [-12.848780049610825, -15.586816584431947], [-12.191830027462133, -16.10587720620869], [-11.51393460444532, -16.597268146431706], [-10.816258395827292, -17.06014520204021], [-10.099999999999994, -17.493713156445665], [-9.36638993930437, -17.897227145703265], [-8.616688546009451, -18.26999393817824], [-7.852183797078389, -18.61137312550849], [-7.074189101441238, -18.92077822281763], [-6.284041043576032, -19.197677676288137], [-5.483097087274493, -19.441595776363677], [-7.546044506249055, -26.756255573411398]]);
            }
          }
        }
        color ([0, 0, 0, 1]) {
          translate ([0, 0, 0.4]) {
            linear_extrude (height=0.2){
              polygon (points=[[-26.367542708875312, 7.810421979357864], [-19.65580456479796, 5.822314566430407], [-19.41206765464967, 6.589509038714793], [-19.138398743192642, 7.3465429656786405], [-18.83521980805242, 8.092249055877469], [-18.502998329172154, 8.825477484570028], [-18.142246567992366, 9.545097666664766], [-17.753520777581, 10.249999999999986], [-17.33742034493146, 10.939097576270026], [-16.894586866751325, 11.611327856959067], [-16.425703160166755, 12.265654311698492], [-15.931492209867912, 12.901068016521656], [-15.412716053319043, 13.516589209551402], [-14.870174605751915, 14.111268801721938], [-19.947795202837934, 18.929750831578207], [-20.675594705671887, 18.13200991525188], [-21.371513940066713, 17.306310753870513], [-22.03447984900418, 16.453926515693098], [-22.663470187105435, 15.576171515432893], [-23.257515096859272, 14.674399187679304], [-23.815698604072075, 13.749999999999982], [-24.337160030233665, 12.804399308940539], [-24.821095319621183, 11.839055162228085], [-25.26675827909471, 10.855456050567335], [-25.673461728673058, 9.855118612495737], [-26.040578561115417, 8.839585295836917], [-26.367542708875312, 7.810421979357864], [-26.756255573411398, 7.5460445062490535], [-26.420566306970805, 8.648333713436307], [-26.039486861105466, 9.735765199013166], [-25.61367192520476, 10.806470770236581], [-25.143853043631445, 11.85861096926052], [-24.63083735893816, 12.890378233300073], [-24.075506225207405, 13.899999999999983], [-23.47881369389694, 14.885741752673182], [-22.84178487479216, 15.84591000017721], [-22.165514174881277, 16.778855186309265], [-21.451163418178623, 17.68297452372183], [-20.699959849726685, 18.55671474748974], [-19.91319402720541, 19.398574783598686], [-14.469299257178031, 14.095367288801922], [-15.040978020305001, 13.483656039542904], [-15.586816584431947, 12.848780049610825], [-16.105877206208696, 12.191830027462126], [-16.597268146431713, 11.51393460444531], [-17.06014520204022, 10.816258395827274], [-17.49371315644567, 10.099999999999985], [-17.897227145703265, 9.36638993930437], [-18.269993938178242, 8.61668854600944], [-18.611373125508493, 7.852183797078379], [-18.92077822281764, 7.07418910144122], [-19.197677676288137, 6.284041043576021], [-19.441595776363677, 5.483097087274492], [-26.756255573411398, 7.5460445062490535]]);
            }
          }
        }
      }
      color ([1, 1, 1, 0.75]) {
        difference () {
          union () {
            difference () {
              difference () {
                union () {
                  cylinder ($fn=144, h=0.4, r=12);
                  translate ([0, 0, 1.5625]) {
                    scale ([1, 1, 0.75]) {
                      union () {
                        translate ([0.0, 8.125, 0]) {
                          rotate ([90.0,0.0,-0.0]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([4.062499999999999, 7.036456405748565, 0]) {
                          rotate ([90.0,0.0,-29.999999999999996]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([7.036456405748564, 4.062500000000001, 0]) {
                          rotate ([90.0,0.0,-59.99999999999999]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([8.125, 4.975127621536122E-16, 0]) {
                          rotate ([90.0,0.0,-90.0]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([7.036456405748565, -4.062499999999998, 0]) {
                          rotate ([90.0,0.0,-119.99999999999999]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([4.062500000000003, -7.036456405748563, 0]) {
                          rotate ([90.0,0.0,-149.99999999999997]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([9.950255243072245E-16, -8.125, 0]) {
                          rotate ([90.0,0.0,-180.0]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([-4.062499999999997, -7.036456405748566, 0]) {
                          rotate ([90.0,0.0,-209.99999999999997]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([-7.036456405748563, -4.0625000000000036, 0]) {
                          rotate ([90.0,0.0,-239.99999999999997]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([-8.125, -1.4925382864608366E-15, 0]) {
                          rotate ([90.0,0.0,-270.0]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([-7.0364564057485675, 4.062499999999995, 0]) {
                          rotate ([90.0,0.0,-299.99999999999994]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                        translate ([-4.0625000000000036, 7.036456405748562, 0]) {
                          rotate ([90.0,0.0,-329.99999999999994]) {
                            sphere ($fn=36, r=2.75);
                          }
                        }
                      }
                    }
                  }
                }
                translate ([0, 0, 1.5625]) {
                  union () {
                    union () {
                      translate ([0.0, 8.875, 0]) {
                        rotate ([90.0,0.0,-0.0]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([4.437499999999999, 7.685975458586894, 0]) {
                        rotate ([90.0,0.0,-29.999999999999996]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([7.685975458586893, 4.437500000000001, 0]) {
                        rotate ([90.0,0.0,-59.99999999999999]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([8.875, 5.43437017121638E-16, 0]) {
                        rotate ([90.0,0.0,-90.0]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([7.685975458586894, -4.437499999999998, 0]) {
                        rotate ([90.0,0.0,-119.99999999999999]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([4.437500000000003, -7.685975458586891, 0]) {
                        rotate ([90.0,0.0,-149.99999999999997]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([1.086874034243276E-15, -8.875, 0]) {
                        rotate ([90.0,0.0,-180.0]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([-4.437499999999997, -7.685975458586895, 0]) {
                        rotate ([90.0,0.0,-209.99999999999997]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([-7.685975458586891, -4.4375000000000036, 0]) {
                        rotate ([90.0,0.0,-239.99999999999997]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([-8.875, -1.6303110513649138E-15, 0]) {
                        rotate ([90.0,0.0,-270.0]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([-7.6859754585868965, 4.437499999999994, 0]) {
                        rotate ([90.0,0.0,-299.99999999999994]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                      translate ([-4.4375000000000036, 7.68597545858689, 0]) {
                        rotate ([90.0,0.0,-329.99999999999994]) {
                          sphere ($fn=36, r=1.45);
                        }
                      }
                    }
                    union () {
                      translate ([0.0, 7.5, 0]) {
                        rotate ([90.0,0.0,-0.0]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([3.7499999999999996, 6.49519052838329, 0]) {
                        rotate ([90.0,0.0,-29.999999999999996]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([6.495190528383289, 3.750000000000001, 0]) {
                        rotate ([90.0,0.0,-59.99999999999999]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([7.5, 4.592425496802575E-16, 0]) {
                        rotate ([90.0,0.0,-90.0]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([6.49519052838329, -3.7499999999999982, 0]) {
                        rotate ([90.0,0.0,-119.99999999999999]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([3.7500000000000027, -6.495190528383288, 0]) {
                        rotate ([90.0,0.0,-149.99999999999997]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([9.18485099360515E-16, -7.5, 0]) {
                        rotate ([90.0,0.0,-180.0]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([-3.749999999999998, -6.495190528383291, 0]) {
                        rotate ([90.0,0.0,-209.99999999999997]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([-6.495190528383288, -3.7500000000000036, 0]) {
                        rotate ([90.0,0.0,-239.99999999999997]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([-7.5, -1.3777276490407722E-15, 0]) {
                        rotate ([90.0,0.0,-270.0]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([-6.495190528383293, 3.749999999999995, 0]) {
                        rotate ([90.0,0.0,-299.99999999999994]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                      translate ([-3.7500000000000036, 6.495190528383288, 0]) {
                        rotate ([90.0,0.0,-329.99999999999994]) {
                          sphere ($fn=36, r=1.34375);
                        }
                      }
                    }
                  }
                }
              }
              cylinder ($fn=144, h=31.25, r=7.75, center=true);
              translate ([0, 0, 3.125]) {
                cylinder ($fn=144, h=1, r=12);
              }
            }
          }
          translate ([0, 0, -10]) {
            cylinder (h=10, r=24);
          }
        }
      }
    }
  }
}