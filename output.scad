union () {
  translate ([0, 0, 0.2]) {
    translate ([0.0, 24.0, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("0", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([11.999999999999998, 20.784609690826528, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("1", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([20.784609690826528, 12.000000000000004, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("2", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([24.0, 1.4695761589768238E-15, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("3", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([20.784609690826528, -11.999999999999995, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("4", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([12.000000000000007, -20.784609690826525, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("5", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([2.9391523179536475E-15, -24.0, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("6", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([-11.999999999999993, -20.78460969082653, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("7", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([-20.784609690826525, -12.00000000000001, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("8", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([-24.0, -4.408728476930472E-15, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("9", size=4, font="JetBrains Mono", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([-20.784609690826535, 11.999999999999984, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("૪", size=4, font="Segoe UI", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.2]) {
    translate ([-12.00000000000001, 20.78460969082652, 0]) {
      color ([0, 0, 0, 1]) {
        linear_extrude (height=0.4, center=true){
          text ("Ɛ", size=4, font="Segoe UI", halign="center", valign="center");
        }
      }
    }
  }
  translate ([0, 0, 0.3]) {
    color ([0, 0, 0, 1]) {
      difference () {
        cylinder ($fn=144, h=0.4, r=4, center=true);
        cylinder ($fn=144, h=1, r=1, center=true);
      }
    }
  }
  translate ([0, 0, 0.3]) {
    color ([0, 0, 0, 1]) {
      difference () {
        cylinder ($fn=144, h=0.4, r=28, center=true);
        cylinder ($fn=144, h=1, r=27.2, center=true);
      }
    }
  }
  color ([1, 1, 1, 1]) {
    difference () {
      cylinder ($fn=144, h=0.3, r=28, center=true);
      cylinder ($fn=144, h=1, r=1, center=true);
    }
  }
}
