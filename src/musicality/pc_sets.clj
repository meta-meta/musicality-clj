(ns musicality.pc-sets)

(def pc-sets
  "The table of pitch class sets from Larry Solomon
  https://web.archive.org/web/20170710011331/http://solomonsmusic.net/pcsets.htm

  *	The set is a mirror (see Glossary).
  <	The set has a complement with the same name ending1
  (4)	(1) The set has only 4 different unique transpositions, as opposed to the normal 12. (2) when occurring after 'comb', indicates the transposition of comb when the set is in prime form.
  B	(at the end of a set-name) The set is the inverse of the set without the B and has the same IV.
..15	This is a Z set whose counterpart (having the same IV) is ordinal number 15 of the same cardinality. For hexachords this number also designates the set complement.
  comb  	This is a hexachord that is combinatorial.
  comb RI	This hexachord is combinatorial with RI (the number in parentheses indicates the transposition of comb form when set is prime form)
  all-comb	The set is known as an all-combinatorial set (see Glossary).
  AAAA5	The last two sets in the table have their IVs designated in hexadecimal; A=10, B=11, C=12
"
  [{:id 0,
    :name "0-1",
    :prime-form [],
    :interval-vec "000000",
    :description "Null set"}
   {:id 1,
    :name "1-1*",
    :prime-form [:0],
    :interval-vec "000000",
    :description "Unison"}
   {:id 2,
    :name "2-1*",
    :prime-form [:0 :1],
    :interval-vec "100000",
    :description "Semitone"}
   {:id 3,
    :name "2-2*",
    :prime-form [:0 :2],
    :interval-vec "010000",
    :description "Whole-tone"}
   {:id 4,
    :name "2-3*",
    :prime-form [:0 :3],
    :interval-vec "001000",
    :description "Minor Third"}
   {:id 5,
    :name "2-4*",
    :prime-form [:0 :4],
    :interval-vec "000100",
    :description "Major Third"}
   {:id 6,
    :name "2-5*",
    :prime-form [:0 :5],
    :interval-vec "000010",
    :description "Perfect Fourth"}
   {:id 7,
    :name "2-6*(6)",
    :prime-form [:0 :6],
    :interval-vec "000001",
    :description "Tritone"}
   {:id 8,
    :name "3-1*",
    :prime-form [:0 :1 :2],
    :interval-vec "210000",
    :description "BACH /Chromatic Trimirror"}
   {:id 9,
    :name "3-2",
    :prime-form [:0 :1 :3],
    :interval-vec "111000",
    :description "Phrygian Trichord"}
   {:id 10,
    :name "3-2B",
    :prime-form [:0 :2 :3],
    :interval-vec "111000",
    :description "Minor Trichord"}
   {:id 11,
    :name "3-3",
    :prime-form [:0 :1 :4],
    :interval-vec "101100",
    :description "Major-minor Trichord.1"}
   {:id 12,
    :name "3-3B",
    :prime-form [:0 :3 :4],
    :interval-vec "101100",
    :description "Major-minor Trichord.2"}
   {:id 13,
    :name "3-4",
    :prime-form [:0 :1 :5],
    :interval-vec "100110",
    :description "Incomplete Major-seventh Chord.1"}
   {:id 14,
    :name "3-4B",
    :prime-form [:0 :4 :5],
    :interval-vec "100110",
    :description "Incomplete Major-seventh Chord.2"}
   {:id 15,
    :name "3-5",
    :prime-form [:0 :1 :6],
    :interval-vec "100011",
    :description "Rite chord.2, Tritone-fourth.1"}
   {:id 16,
    :name "3-5B",
    :prime-form [:0 :5 :6],
    :interval-vec "100011",
    :description "Rite chord.1, Tritone-fourth.2"}
   {:id 17,
    :name "3-6*",
    :prime-form [:0 :2 :4],
    :interval-vec "020100",
    :description "Whole-tone Trichord"}
   {:id 18,
    :name "3-7",
    :prime-form [:0 :2 :5],
    :interval-vec "011010",
    :description "Incomplete Minor-seventh Chord"}
   {:id 19,
    :name "3-7B",
    :prime-form [:0 :3 :5],
    :interval-vec "011010",
    :description "Incomplete Dominant-seventh Chord.2"}
   {:id 20,
    :name "3-8",
    :prime-form [:0 :2 :6],
    :interval-vec "010101",
    :description "Incomplete Dominant-seventh Chord.1/Italian-sixth"}
   {:id 21,
    :name "3-8B",
    :prime-form [:0 :4 :6],
    :interval-vec "010101",
    :description "Incomplete Half-dim-seventh Chord"}
   {:id 22,
    :name "3-9*",
    :prime-form [:0 :2 :7],
    :interval-vec "010020",
    :description "Quartal Trichord"}
   {:id 23,
    :name "3-10*",
    :prime-form [:0 :3 :6],
    :interval-vec "002001",
    :description "Diminished Chord"}
   {:id 24,
    :name "3-11",
    :prime-form [:0 :3 :7],
    :interval-vec "001110",
    :description "Minor Chord"}
   {:id 25,
    :name "3-11B",
    :prime-form [:0 :4 :7],
    :interval-vec "001110",
    :description "Major Chord"}
   {:id 26,
    :name "3-12*(4)",
    :prime-form [:0 :4 :8],
    :interval-vec "000300",
    :description "Augmented Chord"}
   {:id 27,
    :name "4-1*",
    :prime-form [:0 :1 :2 :3],
    :interval-vec "321000",
    :description "BACH /Chromatic Tetramirror"}
   {:id 28,
    :name "4-2",
    :prime-form [:0 :1 :2 :4],
    :interval-vec "221100",
    :description "Major-second Tetracluster.2"}
   {:id 29,
    :name "4-2B",
    :prime-form [:0 :2 :3 :4],
    :interval-vec "221100",
    :description "Major-second Tetracluster.1"}
   {:id 30,
    :name "4-3*",
    :prime-form [:0 :1 :3 :4],
    :interval-vec "212100",
    :description "Alternating Tetramirror"}
   {:id 31,
    :name "4-4",
    :prime-form [:0 :1 :2 :5],
    :interval-vec "211110",
    :description "Minor Third Tetracluster.2"}
   {:id 32,
    :name "4-4B",
    :prime-form [:0 :3 :4 :5],
    :interval-vec "211110",
    :description "Minor Third Tetracluster.1"}
   {:id 33,
    :name "4-5",
    :prime-form [:0 :1 :2 :6],
    :interval-vec "210111",
    :description "Major Third Tetracluster.2"}
   {:id 34,
    :name "4-5B",
    :prime-form [:0 :4 :5 :6],
    :interval-vec "210111",
    :description "Major Third Tetracluster.1"}
   {:id 35,
    :name "4-6*",
    :prime-form [:0 :1 :2 :7],
    :interval-vec "210021",
    :description "Perfect Fourth Tetramirror"}
   {:id 36,
    :name "4-7*",
    :prime-form [:0 :1 :4 :5],
    :interval-vec "201210",
    :description "Arabian Tetramirror"}
   {:id 37,
    :name "4-8*",
    :prime-form [:0 :1 :5 :6],
    :interval-vec "200121",
    :description "Double Fourth Tetramirror"}
   {:id 38,
    :name "4-9*(6)",
    :prime-form [:0 :1 :6 :7],
    :interval-vec "200022",
    :description "Double Tritone Tetramirror"}
   {:id 39,
    :name "4-10*",
    :prime-form [:0 :2 :3 :5],
    :interval-vec "122010",
    :description "Minor Tetramirror"}
   {:id 40,
    :name "4-11",
    :prime-form [:0 :1 :3 :5],
    :interval-vec "121110",
    :description "Phrygian Tetrachord"}
   {:id 41,
    :name "4-11B",
    :prime-form [:0 :2 :4 :5],
    :interval-vec "121110",
    :description "Major Tetrachord"}
   {:id 42,
    :name "4-12<",
    :prime-form [:0 :2 :3 :6],
    :interval-vec "112101",
    :description "Harmonic-minor Tetrachord"}
   {:id 43,
    :name "4-12B<",
    :prime-form [:0 :3 :4 :6],
    :interval-vec "112101",
    :description "Major-third Diminished Tetrachord"}
   {:id 44,
    :name "4-13",
    :prime-form [:0 :1 :3 :6],
    :interval-vec "112011",
    :description "Minor-second Diminished Tetrachord"}
   {:id 45,
    :name "4-13B",
    :prime-form [:0 :3 :5 :6],
    :interval-vec "112011",
    :description "Perfect-fourth Diminished Tetrachord"}
   {:id 46,
    :name "4-14<",
    :prime-form [:0 :2 :3 :7],
    :interval-vec "111120",
    :description "Major-second Minor Tetrachord"}
   {:id 47,
    :name "4-14B<",
    :prime-form [:0 :4 :5 :7],
    :interval-vec "111120",
    :description "Perfect-fourth Major Tetrachord"}
   {:id 48,
    :name "4-Z15..29",
    :prime-form [:0 :1 :4 :6],
    :interval-vec "111111",
    :description "All-interval Tetrachord.1"}
   {:id 49,
    :name "4-Z15B..29",
    :prime-form [:0 :2 :5 :6],
    :interval-vec "111111",
    :description "All-interval Tetrachord.2"}
   {:id 50,
    :name "4-16",
    :prime-form [:0 :1 :5 :7],
    :interval-vec "110121",
    :description "Minor-second Quartal Tetrachord"}
   {:id 51,
    :name "4-16B",
    :prime-form [:0 :2 :6 :7],
    :interval-vec "110121",
    :description "Tritone Quartal Tetrachord"}
   {:id 52,
    :name "4-17*",
    :prime-form [:0 :3 :4 :7],
    :interval-vec "102210",
    :description "Major-minor Tetramirror"}
   {:id 53,
    :name "4-18",
    :prime-form [:0 :1 :4 :7],
    :interval-vec "102111",
    :description "Major-diminished Tetrachord"}
   {:id 54,
    :name "4-18B",
    :prime-form [:0 :3 :6 :7],
    :interval-vec "102111",
    :description "Minor-diminished Tetrachord"}
   {:id 55,
    :name "4-19",
    :prime-form [:0 :1 :4 :8],
    :interval-vec "101310",
    :description "Minor-augmented Tetrachord"}
   {:id 56,
    :name "4-19B",
    :prime-form [:0 :3 :4 :8],
    :interval-vec "101310",
    :description "Augmented-major Tetrachord"}
   {:id 57,
    :name "4-20*",
    :prime-form [:0 :1 :5 :8],
    :interval-vec "101220",
    :description "Major-seventh Chord"}
   {:id 58,
    :name "4-21*",
    :prime-form [:0 :2 :4 :6],
    :interval-vec "030201",
    :description "Whole-tone Tetramirror"}
   {:id 59,
    :name "4-22",
    :prime-form [:0 :2 :4 :7],
    :interval-vec "021120",
    :description "Major-second Major Tetrachord"}
   {:id 60,
    :name "4-22B",
    :prime-form [:0 :3 :5 :7],
    :interval-vec "021120",
    :description "Perfect-fourth Minor Tetrachord"}
   {:id 61,
    :name "4-23*",
    :prime-form [:0 :2 :5 :7],
    :interval-vec "021030",
    :description "Quartal Tetramirror"}
   {:id 62,
    :name "4-24*",
    :prime-form [:0 :2 :4 :8],
    :interval-vec "020301",
    :description "Augmented Seventh Chord"}
   {:id 63,
    :name "4-25*(6)",
    :prime-form [:0 :2 :6 :8],
    :interval-vec "020202",
    :description "French-sixth Chord"}
   {:id 64,
    :name "4-26*",
    :prime-form [:0 :3 :5 :8],
    :interval-vec "012120",

    :description "Minor-seventh Chord"}
   {:id 65,
    :name "4-27",
    :prime-form [:0 :2 :5 :8],
    :interval-vec "012111",
    :description "Half-diminished Seventh Chord"}
   {:id 66,
    :name "4-27B",
    :prime-form [:0 :3 :6 :8],
    :interval-vec "012111",
    :description "Dominant-seventh/German-sixth Chord"}
   {:id 67,
    :name "4-28*(3)",
    :prime-form [:0 :3 :6 :9],
    :interval-vec "004002",
    :description "Diminished-seventh Chord"}
   {:id 68,
    :name "4-Z29..15",
    :prime-form [:0 :1 :3 :7],
    :interval-vec "111111",
    :description "All-interval Tetrachord.3"}
   {:id 69,
    :name "4-Z29B..15",
    :prime-form [:0 :4 :6 :7],
    :interval-vec "111111",
    :description "All-interval Tetrachord.4"}
   {:id 70,
    :name "5-1*",
    :prime-form [:0 :1 :2 :3 :4],
    :interval-vec "432100",
    :description "Chromatic Pentamirror"}
   {:id 71,
    :name "5-2",
    :prime-form [:0 :1 :2 :3 :5],
    :interval-vec "332110",
    :description "Major-second Pentacluster.2"}
   {:id 72,
    :name "5-2B",
    :prime-form [:0 :2 :3 :4 :5],
    :interval-vec "332110",
    :description "Major-second Pentacluster.1"}
   {:id 73,
    :name "5-3",
    :prime-form [:0 :1 :2 :4 :5],
    :interval-vec "322210",
    :description "Minor-second Major Pentachord"}
   {:id 74,
    :name "5-3B",
    :prime-form [:0 :1 :3 :4 :5],
    :interval-vec "322210",
    :description "Spanish Pentacluster"}
   {:id 75,
    :name "5-4",
    :prime-form [:0 :1 :2 :3 :6],
    :interval-vec "322111",
    :description "Blues Pentacluster"}
   {:id 76,
    :name "5-4B",
    :prime-form [:0 :3 :4 :5 :6],
    :interval-vec "322111",
    :description "Minor-third Pentacluster"}
   {:id 77,
    :name "5-5",
    :prime-form [:0 :1 :2 :3 :7],
    :interval-vec "321121",
    :description "Major-third Pentacluster.2"}
   {:id 78,
    :name "5-5B",
    :prime-form [:0 :4 :5 :6 :7],
    :interval-vec "321121",
    :description "Major-third Pentacluster.1"}
   {:id 79,
    :name "5-6",
    :prime-form [:0 :1 :2 :5 :6],
    :interval-vec "311221",
    :description "Oriental Pentacluster.1, Raga Megharanji (13161)"}
   {:id 80,
    :name "5-6B",
    :prime-form [:0 :1 :4 :5 :6],
    :interval-vec "311221",
    :description "Oriental Pentacluster.2"}
   {:id 81,
    :name "5-7",
    :prime-form [:0 :1 :2 :6 :7],
    :interval-vec "310132",
    :description "DoublePentacluster.1, Raga Nabhomani (11415)"}
   {:id 82,
    :name "5-7B",
    :prime-form [:0 :1 :5 :6 :7],
    :interval-vec "310132",
    :description "Double Pentacluster.2"}
   {:id 83,
    :name "5-8*",
    :prime-form [:0 :2 :3 :4 :6],
    :interval-vec "232201",
    :description "Tritone-Symmetric Pentamirror"}
   {:id 84,
    :name "5-9",
    :prime-form [:0 :1 :2 :4 :6],
    :interval-vec "231211",
    :description "Tritone-Expanding Pentachord"}
   {:id 85,
    :name "5-9B",
    :prime-form [:0 :2 :4 :5 :6],
    :interval-vec "231211",
    :description "Tritone-Contracting Pentachord"}
   {:id 86,
    :name "5-10",
    :prime-form [:0 :1 :3 :4 :6],
    :interval-vec "223111",
    :description "Alternating Pentachord.1"}
   {:id 87,
    :name "5-10B",
    :prime-form [:0 :2 :3 :5 :6],
    :interval-vec "223111",
    :description "Alternating Pentachord.2"}
   {:id 88,
    :name "5-11",
    :prime-form [:0 :2 :3 :4 :7],
    :interval-vec "222220",
    :description "Center-cluster Pentachord.1"}
   {:id 89,
    :name "5-11B",
    :prime-form [:0 :3 :4 :5 :7],
    :interval-vec "222220",
    :description "Center-cluster Pentachord.2"}
   {:id 90,
    :name "5-Z12*..36",
    :prime-form [:0 :1 :3 :5 :6],
    :interval-vec "222121",
    :description "Locrian Pentamirror"}
   {:id 91,
    :name "5-13",
    :prime-form [:0 :1 :2 :4 :8],
    :interval-vec "221311",
    :description "Augmented Pentacluster.1"}
   {:id 92,
    :name "5-13B",
    :prime-form [:0 :2 :3 :4 :8],
    :interval-vec "221311",
    :description "Augmented Pentacluster.2"}
   {:id 93,
    :name "5-14",
    :prime-form [:0 :1 :2 :5 :7],
    :interval-vec "221131",
    :description "Double-seconds Triple-fourth Pentachord.1"}
   {:id 94,
    :name "5-14B",
    :prime-form [:0 :2 :5 :6 :7],
    :interval-vec "221131",
    :description "Double-seconds Triple-fourth Pentachord.2"}
   {:id 95,
    :name "5-15*",
    :prime-form [:0 :1 :2 :6 :8],
    :interval-vec "220222",
    :description "Assymetric Pentamirror"}
   {:id 96,
    :name "5-16",
    :prime-form [:0 :1 :3 :4 :7],
    :interval-vec "213211",
    :description "Major-minor-dim Pentachord.1"}
   {:id 97,
    :name "5-16B",
    :prime-form [:0 :3 :4 :6 :7],
    :interval-vec "213211",
    :description "Major-minor-dim Pentachord.2"}
   {:id 98,
    :name "5-Z17*..37",
    :prime-form [:0 :1 :3 :4 :8],
    :interval-vec "212320",
    :description "Minor-major Ninth Chord"}
   {:id 99,
    :name "5-Z18<..38",
    :prime-form [:0 :1 :4 :5 :7],
    :interval-vec "212221",
    :description "Gypsy Pentachord.1"}
   {:id 100,
    :name "5-Z18B<..38",
    :prime-form [:0 :2 :3 :6 :7],
    :interval-vec "212221",
    :description "Gypsy Pentachord.2"}
   {:id 101,
    :name "5-19",
    :prime-form [:0 :1 :3 :6 :7],
    :interval-vec "212122",
    :description "Javanese Pentachord"}
   {:id 102,
    :name "5-19B",
    :prime-form [:0 :1 :4 :6 :7],
    :interval-vec "212122",
    :description "Balinese Pentachord"}
   {:id 103,
    :name "5-20",
    :prime-form [:0 :1 :3 :7 :8],
    :interval-vec "211231",
    :description
    "Balinese Pelog Pentatonic (12414), Raga Bhupala, Raga Bibhas"}
   {:id 104,
    :name "5-20B",
    :prime-form [:0 :1 :5 :7 :8],
    :interval-vec "211231",
    :description
    "Hirajoshi Pentatonic (21414), Iwato (14142), Sakura/Raga Saveri (14214)"}
   {:id 105,
    :name "5-21",
    :prime-form [:0 :1 :4 :5 :8],
    :interval-vec "202420",
    :description
    "Syrian Pentatonic/Major-augmented Ninth Chord, Raga Megharanji (13134)"}
   {:id 106,
    :name "5-21B",
    :prime-form [:0 :3 :4 :7 :8],
    :interval-vec "202420",
    :description "Lebanese Pentachord/Augmented-minor Chord"}
   {:id 107,
    :name "5-22*",
    :prime-form [:0 :1 :4 :7 :8],
    :interval-vec "202321",
    :description "Persian Pentamirror, Raga reva/Ramkali (13314)"}
   {:id 108,
    :name "5-23",
    :prime-form [:0 :2 :3 :5 :7],
    :interval-vec "132130",
    :description "Minor Pentachord"}
   {:id 109,
    :name "5-23B",
    :prime-form [:0 :2 :4 :5 :7],
    :interval-vec "132130",
    :description "Major Pentachord"}
   {:id 110,
    :name "5-24",
    :prime-form [:0 :1 :3 :5 :7],
    :interval-vec "131221",
    :description "Phrygian Pentachord"}
   {:id 111,
    :name "5-24B",
    :prime-form [:0 :2 :4 :6 :7],
    :interval-vec "131221",
    :description "Lydian Pentachord"}
   {:id 112,
    :name "5-25",
    :prime-form [:0 :2 :3 :5 :8],
    :interval-vec "123121",
    :description "Diminished-major Ninth Chord"}
   {:id 113,
    :name "5-23B",
    :prime-form [:0 :3 :5 :6 :8],
    :interval-vec "123121",
    :description "Minor-diminished Ninth Chord"}
   {:id 114,
    :name "5-26<",
    :prime-form [:0 :2 :4 :5 :8],
    :interval-vec "122311",
    :description "Diminished-augmented Ninth Chord"}
   {:id 115,
    :name "5-26B<",
    :prime-form [:0 :3 :4 :6 :8],
    :interval-vec "122311",
    :description "Augmented-diminished Ninth Chord"}
   {:id 116,
    :name "5-27",
    :prime-form [:0 :1 :3 :5 :8],
    :interval-vec "122230",
    :description "Major-Ninth Chord"}
   {:id 117,
    :name "5-27B",
    :prime-form [:0 :3 :5 :7 :8],
    :interval-vec "122230",
    :description "Minor-Ninth Chord"}
   {:id 118,
    :name "5-28<",
    :prime-form [:0 :2 :3 :6 :8],
    :interval-vec "122212",
    :description "Augmented-sixth Pentachord.1"}
   {:id 119,
    :name "5-28B<",
    :prime-form [:0 :2 :5 :6 :8],
    :interval-vec "122212",
    :description "Augmented-sixth Pentachord.2"}
   {:id 120,
    :name "5-29",
    :prime-form [:0 :1 :3 :6 :8],
    :interval-vec "122131",
    :description "Kumoi Pentachord.2"}
   {:id 121,
    :name "5-29B",
    :prime-form [:0 :2 :5 :7 :8],
    :interval-vec "122131",
    :description "Kumoi Pentachord.1"}
   {:id 122,
    :name "5-30",
    :prime-form [:0 :1 :4 :6 :8],
    :interval-vec "121321",
    :description "Enigmatic Pentachord.1"}
   {:id 123,
    :name "5-30B",
    :prime-form [:0 :2 :4 :7 :8],
    :interval-vec "121321",
    :description "Enigmatic Pentachord.2, Altered Pentatonic (14223)"}
   {:id 124,
    :name "5-31",
    :prime-form [:0 :1 :3 :6 :9],
    :interval-vec "114112",
    :description "Diminished Minor-Ninth Chord"}
   {:id 125,
    :name "5-31B",
    :prime-form [:0 :2 :3 :6 :9],
    :interval-vec "114112",
    :description "Ranjaniraga/Flat-Ninth Pentachord"}
   {:id 126,
    :name "5-32",
    :prime-form [:0 :1 :4 :6 :9],
    :interval-vec "113221",
    :description "Neapolitan Pentachord.1"}
   {:id 127,
    :name "5-32B",
    :prime-form [:0 :1 :4 :7 :9],
    :interval-vec "113221",
    :description "Neapolitan Pentachord.2"}
   {:id 128,
    :name "5-33*",
    :prime-form [:0 :2 :4 :6 :8],
    :interval-vec "040402",
    :description "Whole-tone Pentamirror"}
   {:id 129,
    :name "5-34*",
    :prime-form [:0 :2 :4 :6 :9],
    :interval-vec "032221",
    :description
    "Dominant-ninth/major-minor/Prometheus Pentamirror, Dominant Pentatonic (22332)"}
   {:id 130,
    :name "5-35*",
    :prime-form [:0 :2 :4 :7 :9],
    :interval-vec "032140",
    :description
    "'Black Key' Pentatonic/Slendro/Bilahariraga/Quartal Pentamirror, Yo (23232)"}
   {:id 131,
    :name "5-Z36..12",
    :prime-form [:0 :1 :2 :4 :7],
    :interval-vec "222121",
    :description "Major-seventh Pentacluster.2"}
   {:id 132,
    :name "5-Z36B..12",
    :prime-form [:0 :3 :5 :6 :7],
    :interval-vec "222121",
    :description "Minor-seventh Pentacluster.1"}
   {:id 133,
    :name "5-Z37*..17",
    :prime-form [:0 :3 :4 :5 :8],
    :interval-vec "212320",
    :description "Center-cluster Pentamirror"}
   {:id 134,
    :name "5-Z38..18",
    :prime-form [:0 :1 :2 :5 :8],
    :interval-vec "212221",
    :description "Diminished Pentacluster.1"}
   {:id 135,
    :name "5-Z38B..18",
    :prime-form [:0 :3 :6 :7 :8],
    :interval-vec "212221",
    :description "Diminished Pentacluster.2"}
   {:id 136,
    :name "6-1*",
    :prime-form [:0 :1 :2 :3 :4 :5],
    :interval-vec "543210",
    :description
    "Chromatic Hexamirror/1st ord. all-comb (P6, Ib, RI5)"}
   {:id 137,
    :name "6-2",
    :prime-form [:0 :1 :2 :3 :4 :6],
    :interval-vec "443211",
    :description "comb I (b)"}
   {:id 138,
    :name "6-2B",
    :prime-form [:0 :2 :3 :4 :5 :6],
    :interval-vec "443211",
    :description "comb I (1)"}
   {:id 139,
    :name "6-Z3..36B",
    :prime-form [:0 :1 :2 :3 :5 :6],
    :interval-vec "433221",
    :description ""}
   {:id 140,
    :name "6-Z3B..36",
    :prime-form [:0 :1 :3 :4 :5 :6],
    :interval-vec "433221",
    :description ""}
   {:id 141,
    :name "6-Z4*..37",
    :prime-form [:0 :1 :2 :4 :5 :6],
    :interval-vec "432321",
    :description "comb RI (6)"}
   {:id 142,
    :name "6-5",
    :prime-form [:0 :1 :2 :3 :6 :7],
    :interval-vec "422232",
    :description "comb I (b)"}
   {:id 143,
    :name "6-5B",
    :prime-form [:0 :1 :4 :5 :6 :7],
    :interval-vec "422232",
    :description "comb I (3)"}
   {:id 144,
    :name "6-Z6*..38",
    :prime-form [:0 :1 :2 :5 :6 :7],
    :interval-vec "421242",
    :description "Double-cluster Hexamirror"}
   {:id 145,
    :name "6-7* (6)",
    :prime-form [:0 :1 :2 :6 :7 :8],
    :interval-vec "420243",
    :description
    "Messiaen's mode 5 (114114), 2nd ord.all-comb(P3+9,I5,Ib,R6,RI2+8)"}
   {:id 146,
    :name "6-8*",
    :prime-form [:0 :2 :3 :4 :5 :7],
    :interval-vec "343230",
    :description "1st ord.all-comb (P6, I1, RI7)"}
   {:id 147,
    :name "6-9",
    :prime-form [:0 :1 :2 :3 :5 :7],
    :interval-vec "342231",
    :description "comb I (b)"}
   {:id 148,
    :name "6-9B",
    :prime-form [:0 :2 :4 :5 :6 :7],
    :interval-vec "342231",
    :description "comb I (3)"}
   {:id 149,
    :name "6-Z10..39",
    :prime-form [:0 :1 :3 :4 :5 :7],
    :interval-vec "333321",
    :description ""}
   {:id 150,
    :name "6-Z10B..39B",
    :prime-form [:0 :2 :3 :4 :6 :7],
    :interval-vec "333321",
    :description ""}
   {:id 151,
    :name "6-Z11..40B",
    :prime-form [:0 :1 :2 :4 :5 :7],
    :interval-vec "333231",
    :description ""}
   {:id 152,
    :name "6-Z11B..40",
    :prime-form [:0 :2 :3 :5 :6 :7],
    :interval-vec "333231",
    :description ""}
   {:id 153,
    :name "6-Z12..41B",
    :prime-form [:0 :1 :2 :4 :6 :7],
    :interval-vec "332232",
    :description ""}
   {:id 154,
    :name "6-Z12B..41",
    :prime-form [:0 :1 :3 :5 :6 :7],
    :interval-vec "332232",
    :description ""}
   {:id 155,
    :name "6-Z13*..42",
    :prime-form [:0 :1 :3 :4 :6 :7],
    :interval-vec "324222",
    :description "Alternating Hexamirror/comb RI7)"}
   {:id 156,
    :name "6-14..14",
    :prime-form [:0 :1 :3 :4 :5 :8],
    :interval-vec "323430",
    :description "comb P (6)"}
   {:id 157,
    :name "6-14B..14B",
    :prime-form [:0 :3 :4 :5 :7 :8],
    :interval-vec "323430",
    :description "comb P (6)"}
   {:id 158,
    :name "6-15",
    :prime-form [:0 :1 :2 :4 :5 :8],
    :interval-vec "323421",
    :description "comb I (b)"}
   {:id 159,
    :name "6-15B",
    :prime-form [:0 :3 :4 :6 :7 :8],
    :interval-vec "323421",
    :description "comb I (5)"}
   {:id 160,
    :name "6-16",
    :prime-form [:0 :1 :4 :5 :6 :8],
    :interval-vec "322431",
    :description "comb I (3)"}
   {:id 161,
    :name "6-16B",
    :prime-form [:0 :2 :3 :4 :7 :8],
    :interval-vec "322431",
    :description "Megha or 'Cloud' Raga/comb.I (1)"}
   {:id 162,
    :name "6-Z17..43B",
    :prime-form [:0 :1 :2 :4 :7 :8],
    :interval-vec "322332",
    :description ""}
   {:id 163,
    :name "6-Z17B..43",
    :prime-form [:0 :1 :4 :6 :7 :8],
    :interval-vec "322332",
    :description ""}
   {:id 164,
    :name "6-18",
    :prime-form [:0 :1 :2 :5 :7 :8],
    :interval-vec "322242",
    :description "comb I (b)"}
   {:id 165,
    :name "6-18B",
    :prime-form [:0 :1 :3 :6 :7 :8],
    :interval-vec "322242",
    :description "comb I (5)"}
   {:id 166,
    :name "6-Z19..44B",
    :prime-form [:0 :1 :3 :4 :7 :8],
    :interval-vec "313431",
    :description ""}
   {:id 167,
    :name "6-Z19B..44",
    :prime-form [:0 :1 :4 :5 :7 :8],
    :interval-vec "313431",
    :description ""}
   {:id 168,
    :name "6-20*(4)",
    :prime-form [:0 :1 :4 :5 :8 :9],
    :interval-vec "303630",
    :description
    "Augmented scale, Genus tertium, 3rd ord. all-comb (P2+6+10, I3+7+b, R4+8, RI1+5+9)"}
   {:id 169,
    :name "6-21",
    :prime-form [:0 :2 :3 :4 :6 :8],
    :interval-vec "242412",
    :description "comb I (1)"}
   {:id 170,
    :name "6-21B",
    :prime-form [:0 :2 :4 :5 :6 :8],
    :interval-vec "242412",
    :description "comb I (3)"}
   {:id 171,
    :name "6-22",
    :prime-form [:0 :1 :2 :4 :6 :8],
    :interval-vec "241422",
    :description "comb I (b)"}
   {:id 172,
    :name "6-22B",
    :prime-form [:0 :2 :4 :6 :7 :8],
    :interval-vec "241422",
    :description "comb I (5)"}
   {:id 173,
    :name "6-Z23*..45",
    :prime-form [:0 :2 :3 :5 :6 :8],
    :interval-vec "234222",
    :description "Super-Locrian Hexamirror/comb RI (8)"}
   {:id 174,
    :name "6-Z24..46B",
    :prime-form [:0 :1 :3 :4 :6 :8],
    :interval-vec "233331",
    :description ""}
   {:id 175,
    :name "6-Z24B..46",
    :prime-form [:0 :2 :4 :5 :7 :8],
    :interval-vec "233331",
    :description "Melodic-minor Hexachord"}
   {:id 176,
    :name "6-Z25..47B",
    :prime-form [:0 :1 :3 :5 :6 :8],
    :interval-vec "233241",
    :description "Locrian Hexachord/Suddha Saveriraga"}
   {:id 177,
    :name "6-Z25B..47",
    :prime-form [:0 :2 :3 :5 :7 :8],
    :interval-vec "233241",
    :description "Minor Hexachord"}
   {:id 178,
    :name "6-Z26*..48",
    :prime-form [:0 :1 :3 :5 :7 :8],
    :interval-vec "232341",
    :description "Phrygian Hexamirror/comb RI (8)"}
   {:id 179,
    :name "6-27",
    :prime-form [:0 :1 :3 :4 :6 :9],
    :interval-vec "225222",
    :description "comb I (b)"}
   {:id 180,
    :name "6-27B",
    :prime-form [:0 :2 :3 :5 :6 :9],
    :interval-vec "225222",
    :description "Pyramid Hexachord/comb I (1)"}
   {:id 181,
    :name "6-Z28*..49",
    :prime-form [:0 :1 :3 :5 :6 :9],
    :interval-vec "224322",
    :description "Double-Phrygian Hexachord/comb RI (6)"}
   {:id 182,
    :name "6-Z29*..50",
    :prime-form [:0 :1 :3 :6 :8 :9],
    :interval-vec "224232",
    :description "comb RI (9)"}
   {:id 183,
    :name "6-30 (6)",
    :prime-form [:0 :1 :3 :6 :7 :9],
    :interval-vec "224223",
    :description "Minor-bitonal Hexachord/comb R (6), I (5,b)"}
   {:id 184,
    :name "6-30B (6)",
    :prime-form [:0 :2 :3 :6 :8 :9],
    :interval-vec "224223",
    :description
    "Petrushka chord, Major-bitonal Hexachord, comb R (6), I (1,7)"}
   {:id 185,
    :name "6-31",
    :prime-form [:0 :1 :3 :5 :8 :9],
    :interval-vec "223431",
    :description "comb I (7)"}
   {:id 186,
    :name "6-31B",
    :prime-form [:0 :1 :4 :6 :8 :9],
    :interval-vec "223431",
    :description "comb I (b)"}
   {:id 187,
    :name "6-32*",
    :prime-form [:0 :2 :4 :5 :7 :9],
    :interval-vec "143250",
    :description
    "Arezzo major Diatonic (221223), major hexamirror, quartal hexamirror, 1st ord.all-comb P (6), I (3), RI (9)"}
   {:id 188,
    :name "6-33",
    :prime-form [:0 :2 :3 :5 :7 :9],
    :interval-vec "143241",
    :description "Dorian Hexachord/comb I (1)"}
   {:id 189,
    :name "6-33B",
    :prime-form [:0 :2 :4 :6 :7 :9],
    :interval-vec "143241",
    :description "Dominant-11th/Lydian Hexachord/comb I (5)"}
   {:id 190,
    :name "6-34",
    :prime-form [:0 :1 :3 :5 :7 :9],
    :interval-vec "142422",
    :description
    "Scriabin's Mystic Chord or Prometheus Hexachord/comb I (B)"}
   {:id 191,
    :name "6-34B",
    :prime-form [:0 :2 :4 :6 :8 :9],
    :interval-vec "142422",
    :description "Harmonic Hexachord/Augmented-11th/comb I (7)"}
   {:id 192,
    :name "6-35*(2)",
    :prime-form [:0 :2 :4 :6 :8 :૪],
    :interval-vec "060603",
    :description
    "Wholetone scale/6th ord.all-comb.(P+IoddT, R+RIevenT)"}
   {:id 193,
    :name "6-Z36..3B",
    :prime-form [:0 :1 :2 :3 :4 :7],
    :interval-vec "433221",
    :description ""}
   {:id 194,
    :name "6-Z36B..3",
    :prime-form [:0 :3 :4 :5 :6 :7],
    :interval-vec "433221",
    :description ""}
   {:id 195,
    :name "6-Z37*..4",
    :prime-form [:0 :1 :2 :3 :4 :8],
    :interval-vec "432321",
    :description "comb RI (4)"}
   {:id 196,
    :name "6-Z38*..6",
    :prime-form [:0 :1 :2 :3 :7 :8],
    :interval-vec "421242",
    :description "comb RI (3)"}
   {:id 197,
    :name "6-Z39..10",
    :prime-form [:0 :2 :3 :4 :5 :8],
    :interval-vec "333321",
    :description ""}
   {:id 198,
    :name "6-Z39B..10B",
    :prime-form [:0 :3 :4 :5 :6 :8],
    :interval-vec "333321",
    :description ""}
   {:id 199,
    :name "6-Z40..11B",
    :prime-form [:0 :1 :2 :3 :5 :8],
    :interval-vec "333231",
    :description ""}
   {:id 200,
    :name "6-Z40B..11",
    :prime-form [:0 :3 :5 :6 :7 :8],
    :interval-vec "333231",
    :description ""}
   {:id 201,
    :name "6-Z41..12B",
    :prime-form [:0 :1 :2 :3 :6 :8],
    :interval-vec "332232",
    :description ""}
   {:id 202,
    :name "6-Z41B..12",
    :prime-form [:0 :2 :5 :6 :7 :8],
    :interval-vec "332232",
    :description ""}
   {:id 203,
    :name "6-Z42*..13",
    :prime-form [:0 :1 :2 :3 :6 :9],
    :interval-vec "324222",
    :description "comb RI (3)"}
   {:id 204,
    :name "6-Z43..17B",
    :prime-form [:0 :1 :2 :5 :6 :8],
    :interval-vec "322332",
    :description ""}
   {:id 205,
    :name "6-Z43B..17",
    :prime-form [:0 :2 :3 :6 :7 :8],
    :interval-vec "322332",
    :description ""}
   {:id 206,
    :name "6-Z44..19B",
    :prime-form [:0 :1 :2 :5 :6 :9],
    :interval-vec "313431",
    :description "Schoenberg Anagram Hexachord"}
   {:id 207,
    :name "6-Z44B..19",
    :prime-form [:0 :1 :2 :5 :8 :9],
    :interval-vec "313431",
    :description "Bauli raga (133131)"}
   {:id 208,
    :name "6-Z45*..23",
    :prime-form [:0 :2 :3 :4 :6 :9],
    :interval-vec "234222",
    :description "comb RI (6)"}
   {:id 209,
    :name "6-Z46..24B",
    :prime-form [:0 :1 :2 :4 :6 :9],
    :interval-vec "233331",
    :description ""}
   {:id 210,
    :name "6-Z46B..24",
    :prime-form [:0 :2 :4 :5 :6 :9],
    :interval-vec "233331",
    :description ""}
   {:id 211,
    :name "6-Z47..25B",
    :prime-form [:0 :1 :2 :4 :7 :9],
    :interval-vec "233241",
    :description ""}
   {:id 212,
    :name "6-Z47B..25",
    :prime-form [:0 :2 :3 :4 :7 :9],
    :interval-vec "233241",
    :description "Blues mode.1 (321132)"}
   {:id 213,
    :name "6-Z48*..26",
    :prime-form [:0 :1 :2 :5 :7 :9],
    :interval-vec "232341",
    :description "comb RI (2)"}
   {:id 214,
    :name "6-Z49*..28",
    :prime-form [:0 :1 :3 :4 :7 :9],
    :interval-vec "224322",
    :description "Prometheus Neapolitan mode (132312), comb RI (4)"}
   {:id 215,
    :name "6-Z50*..29",
    :prime-form [:0 :1 :4 :6 :7 :9],
    :interval-vec "224232",
    :description "comb RI (1)"}
   {:id 216,
    :name "7-1*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6],
    :interval-vec "654321",
    :description "Chromatic Heptamirror"}
   {:id 217,
    :name "7-2",
    :prime-form [:0 :1 :2 :3 :4 :5 :7],
    :interval-vec "554331",
    :description ""}
   {:id 218,
    :name "7-2B",
    :prime-form [:0 :2 :3 :4 :5 :6 :7],
    :interval-vec "554331",
    :description ""}
   {:id 219,
    :name "7-3",
    :prime-form [:0 :1 :2 :3 :4 :5 :8],
    :interval-vec "544431",
    :description ""}
   {:id 220,
    :name "7-3B",
    :prime-form [:0 :3 :4 :5 :6 :7 :8],
    :interval-vec "544431",
    :description ""}
   {:id 221,
    :name "7-4",
    :prime-form [:0 :1 :2 :3 :4 :6 :7],
    :interval-vec "544332",
    :description ""}
   {:id 222,
    :name "7-4B",
    :prime-form [:0 :1 :3 :4 :5 :6 :7],
    :interval-vec "544332",
    :description ""}
   {:id 223,
    :name "7-5",
    :prime-form [:0 :1 :2 :3 :5 :6 :7],
    :interval-vec "543342",
    :description ""}
   {:id 224,
    :name "7-5B",
    :prime-form [:0 :1 :2 :4 :5 :6 :7],
    :interval-vec "543342",
    :description ""}
   {:id 225,
    :name "7-6",
    :prime-form [:0 :1 :2 :3 :4 :7 :8],
    :interval-vec "533442",
    :description ""}
   {:id 226,
    :name "7-6B",
    :prime-form [:0 :1 :4 :5 :6 :7 :8],
    :interval-vec "533442",
    :description ""}
   {:id 227,
    :name "7-7",
    :prime-form [:0 :1 :2 :3 :6 :7 :8],
    :interval-vec "532353",
    :description ""}
   {:id 228,
    :name "7-7B",
    :prime-form [:0 :1 :2 :5 :6 :7 :8],
    :interval-vec "532353",
    :description ""}
   {:id 229,
    :name "7-8*",
    :prime-form [:0 :2 :3 :4 :5 :6 :8],
    :interval-vec "454422",
    :description ""}
   {:id 230,
    :name "7-9",
    :prime-form [:0 :1 :2 :3 :4 :6 :8],
    :interval-vec "453432",
    :description ""}
   {:id 231,
    :name "7-9B",
    :prime-form [:0 :2 :4 :5 :6 :7 :8],
    :interval-vec "453432",
    :description ""}
   {:id 232,
    :name "7-10",
    :prime-form [:0 :1 :2 :3 :4 :6 :9],
    :interval-vec "445332",
    :description ""}
   {:id 233,
    :name "7-10B",
    :prime-form [:0 :2 :3 :4 :5 :6 :9],
    :interval-vec "445332",
    :description ""}
   {:id 234,
    :name "7-11",
    :prime-form [:0 :1 :3 :4 :5 :6 :8],
    :interval-vec "444441",
    :description ""}
   {:id 235,
    :name "7-11B",
    :prime-form [:0 :2 :3 :4 :5 :7 :8],
    :interval-vec "444441",
    :description ""}
   {:id 236,
    :name "7-Z12*..36",
    :prime-form [:0 :1 :2 :3 :4 :7 :9],
    :interval-vec "444342",
    :description ""}
   {:id 237,
    :name "7-13",
    :prime-form [:0 :1 :2 :4 :5 :6 :8],
    :interval-vec "443532",
    :description ""}
   {:id 238,
    :name "7-13B",
    :prime-form [:0 :2 :3 :4 :6 :7 :8],
    :interval-vec "443532",
    :description ""}
   {:id 239,
    :name "7-14",
    :prime-form [:0 :1 :2 :3 :5 :7 :8],
    :interval-vec "443352",
    :description ""}
   {:id 240,
    :name "7-14B",
    :prime-form [:0 :1 :3 :5 :6 :7 :8],
    :interval-vec "443352",
    :description ""}
   {:id 241,
    :name "7-15*",
    :prime-form [:0 :1 :2 :4 :6 :7 :8],
    :interval-vec "442443",
    :description ""}
   {:id 242,
    :name "7-16",
    :prime-form [:0 :1 :2 :3 :5 :6 :9],
    :interval-vec "435432",
    :description ""}
   {:id 243,
    :name "7-16B",
    :prime-form [:0 :1 :3 :4 :5 :6 :9],
    :interval-vec "435432",
    :description ""}
   {:id 244,
    :name "7-Z17*..37",
    :prime-form [:0 :1 :2 :4 :5 :6 :9],
    :interval-vec "434541",
    :description ""}
   {:id 245,
    :name "7-Z18<..38",
    :prime-form [:0 :1 :2 :3 :5 :8 :9],
    :interval-vec "434442",
    :description ""}
   {:id 246,
    :name "7-Z18B<..38",
    :prime-form [:0 :1 :4 :6 :7 :8 :9],
    :interval-vec "434442",
    :description ""}
   {:id 247,
    :name "7-19",
    :prime-form [:0 :1 :2 :3 :6 :7 :9],
    :interval-vec "434343",
    :description ""}
   {:id 248,
    :name "7-19B",
    :prime-form [:0 :1 :2 :3 :6 :8 :9],
    :interval-vec "434343",
    :description ""}
   {:id 249,
    :name "7-20",
    :prime-form [:0 :1 :2 :4 :7 :8 :9],
    :interval-vec "433452",
    :description "Chromatic Phrygian inverse (1123113)"}
   {:id 250,
    :name "7-20B",
    :prime-form [:0 :1 :2 :5 :7 :8 :9],
    :interval-vec "433452",
    :description
    "Pantuvarali Raga (1321131), Chromatic Mixolydian (1131132), Chromatic Dorian/Mela Kanakangi (1132113)"}
   {:id 251,
    :name "7-21",
    :prime-form [:0 :1 :2 :4 :5 :8 :9],
    :interval-vec "424641",
    :description ""}
   {:id 252,
    :name "7-21B",
    :prime-form [:0 :1 :3 :4 :5 :8 :9],
    :interval-vec "424641",
    :description "Gypsy hexatonic (1312113)"}
   {:id 253,
    :name "7-22*",
    :prime-form [:0 :1 :2 :5 :6 :8 :9],
    :interval-vec "424542",
    :description
    "Persian, Major Gypsy, Hungarian Minor, Double Harmonic scale, Bhairav That, Mayamdavagaula Raga (all: 1312131), Oriental (1311312)"}
   {:id 254,
    :name "7-23",
    :prime-form [:0 :2 :3 :4 :5 :7 :9],
    :interval-vec "354351",
    :description ""}
   {:id 255,
    :name "7-23B",
    :prime-form [:0 :2 :4 :5 :6 :7 :9],
    :interval-vec "354351",
    :description "Tritone Major Heptachord"}
   {:id 256,
    :name "7-24",
    :prime-form [:0 :1 :2 :3 :5 :7 :9],
    :interval-vec "353442",
    :description ""}
   {:id 257,
    :name "7-24B",
    :prime-form [:0 :2 :4 :6 :7 :8 :9],
    :interval-vec "353442",
    :description "Enigmatic Heptatonic (1322211)"}
   {:id 258,
    :name "7-25",
    :prime-form [:0 :2 :3 :4 :6 :7 :9],
    :interval-vec "345342",
    :description ""}
   {:id 259,
    :name "7-25B",
    :prime-form [:0 :2 :3 :5 :6 :7 :9],
    :interval-vec "345342",
    :description ""}
   {:id 260,
    :name "7-26<",
    :prime-form [:0 :1 :3 :4 :5 :7 :9],
    :interval-vec "344532",
    :description ""}
   {:id 261,
    :name "7-26B<",
    :prime-form [:0 :2 :4 :5 :6 :8 :9],
    :interval-vec "344532",
    :description ""}
   {:id 262,
    :name "7-27",
    :prime-form [:0 :1 :2 :4 :5 :7 :9],
    :interval-vec "344451",
    :description ""}
   {:id 263,
    :name "7-27B",
    :prime-form [:0 :2 :4 :5 :7 :8 :9],
    :interval-vec "344451",
    :description "Modified Blues mode (2121132)"}
   {:id 264,
    :name "7-28<",
    :prime-form [:0 :1 :3 :5 :6 :7 :9],
    :interval-vec "344433",
    :description ""}
   {:id 265,
    :name "7-28B<",
    :prime-form [:0 :2 :3 :4 :6 :8 :9],
    :interval-vec "344433",
    :description ""}
   {:id 266,
    :name "7-29",
    :prime-form [:0 :1 :2 :4 :6 :7 :9],
    :interval-vec "344352",
    :description ""}
   {:id 267,
    :name "7-29B",
    :prime-form [:0 :2 :3 :5 :7 :8 :9],
    :interval-vec "344352",
    :description ""}
   {:id 268,
    :name "7-30",
    :prime-form [:0 :1 :2 :4 :6 :8 :9],
    :interval-vec "343542",
    :description "Neapolitan-Minor mode (1222131), Mela Dhenuka"}
   {:id 269,
    :name "7-30B",
    :prime-form [:0 :1 :3 :5 :7 :8 :9],
    :interval-vec "343542",
    :description ""}
   {:id 270,
    :name "7-31",
    :prime-form [:0 :1 :3 :4 :6 :7 :9],
    :interval-vec "336333",
    :description
    "Alternating Heptachord.1/Hungarian Major mode (3121212)"}
   {:id 271,
    :name "7-31B",
    :prime-form [:0 :2 :3 :5 :6 :8 :9],
    :interval-vec "336333",
    :description "Alternating Heptachord.2"}
   {:id 272,
    :name "7-32",
    :prime-form [:0 :1 :3 :4 :6 :8 :9],
    :interval-vec "335442",
    :description
    "Harmonic-Minor mode (2122131), Spanish Gypsy, Mela Kiravani, Pilu That"}
   {:id 273,
    :name "7-32B",
    :prime-form [:0 :1 :3 :5 :6 :8 :9],
    :interval-vec "335442",
    :description
    "Dharmavati Scale (2131221), Harmonic minor inverse (1312212), Mela Cakravana, Raga Ahir Bhairav"}
   {:id 274,
    :name "7-33*",
    :prime-form [:0 :1 :2 :4 :6 :8 :૪],
    :interval-vec "262623",
    :description
    "Neapolitan-major mode (1222221)/Leading-Whole-tone mode (222211)"}
   {:id 275,
    :name "7-34*",
    :prime-form [:0 :1 :3 :4 :6 :8 :૪],
    :interval-vec "254442",
    :description
    "Harmonic/Super-Locrian, Melodic minor ascending (1212222)/Aug.13th Heptamirror, Jazz Minor"}
   {:id 276,
    :name "7-35*",
    :prime-form [:0 :1 :3 :5 :6 :8 :૪],
    :interval-vec "254361",
    :description
    "Major Diatonic Heptachord/Dominant-13th, Locrian (1221222), Phrygian (1222122), Major inverse"}
   {:id 277,
    :name "7-Z36..12",
    :prime-form [:0 :1 :2 :3 :5 :6 :8],
    :interval-vec "444342",
    :description ""}
   {:id 278,
    :name "7-Z36B..12",
    :prime-form [:0 :2 :3 :5 :6 :7 :8],
    :interval-vec "444342",
    :description ""}
   {:id 279,
    :name "7-Z37*..17",
    :prime-form [:0 :1 :3 :4 :5 :7 :8],
    :interval-vec "434541",
    :description ""}
   {:id 280,
    :name "7-Z38..18",
    :prime-form [:0 :1 :2 :4 :5 :7 :8],
    :interval-vec "434442",
    :description ""}
   {:id 281,
    :name "7-Z38B..18",
    :prime-form [:0 :1 :3 :4 :6 :7 :8],
    :interval-vec "434442",
    :description ""}
   {:id 282,
    :name "8-1*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7],
    :interval-vec "765442",
    :description "Chromatic Octamirror"}
   {:id 283,
    :name "8-2",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :8],
    :interval-vec "665542",
    :description ""}
   {:id 284,
    :name "8-2B",
    :prime-form [:0 :2 :3 :4 :5 :6 :7 :8],
    :interval-vec "665542",
    :description ""}
   {:id 285,
    :name "8-3*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :9],
    :interval-vec "656542",
    :description ""}
   {:id 286,
    :name "8-4",
    :prime-form [:0 :1 :2 :3 :4 :5 :7 :8],
    :interval-vec "655552",
    :description ""}
   {:id 287,
    :name "8-4B",
    :prime-form [:0 :1 :3 :4 :5 :6 :7 :8],
    :interval-vec "655552",
    :description ""}
   {:id 288,
    :name "8-5",
    :prime-form [:0 :1 :2 :3 :4 :6 :7 :8],
    :interval-vec "654553",
    :description ""}
   {:id 289,
    :name "8-5B",
    :prime-form [:0 :1 :2 :4 :5 :6 :7 :8],
    :interval-vec "654553",
    :description ""}
   {:id 290,
    :name "8-6*",
    :prime-form [:0 :1 :2 :3 :5 :6 :7 :8],
    :interval-vec "654463",
    :description ""}
   {:id 291,
    :name "8-7*",
    :prime-form [:0 :1 :2 :3 :4 :5 :8 :9],
    :interval-vec "645652",
    :description ""}
   {:id 292,
    :name "8-8*",
    :prime-form [:0 :1 :2 :3 :4 :7 :8 :9],
    :interval-vec "644563",
    :description ""}
   {:id 293,
    :name "8-9* (6)",
    :prime-form [:0 :1 :2 :3 :6 :7 :8 :9],
    :interval-vec "644464",
    :description "Messiaen's mode 4 (11131113)"}
   {:id 294,
    :name "8-10*",
    :prime-form [:0 :2 :3 :4 :5 :6 :7 :9],
    :interval-vec "566452",
    :description ""}
   {:id 295,
    :name "8-11",
    :prime-form [:0 :1 :2 :3 :4 :5 :7 :9],
    :interval-vec "565552",
    :description ""}
   {:id 296,
    :name "8-11B",
    :prime-form [:0 :2 :4 :5 :6 :7 :8 :9],
    :interval-vec "565552",
    :description ""}
   {:id 297,
    :name "8-12<",
    :prime-form [:0 :1 :3 :4 :5 :6 :7 :9],
    :interval-vec "556543",
    :description ""}
   {:id 298,
    :name "8-12B<",
    :prime-form [:0 :2 :3 :4 :5 :6 :8 :9],
    :interval-vec "556543",
    :description ""}
   {:id 299,
    :name "8-13",
    :prime-form [:0 :1 :2 :3 :4 :6 :7 :9],
    :interval-vec "556453",
    :description ""}
   {:id 300,
    :name "8-13B",
    :prime-form [:0 :2 :3 :5 :6 :7 :8 :9],
    :interval-vec "556453",
    :description ""}
   {:id 301,
    :name "8-14<",
    :prime-form [:0 :1 :2 :4 :5 :6 :7 :9],
    :interval-vec "555562",
    :description ""}
   {:id 302,
    :name "8-14B<",
    :prime-form [:0 :2 :3 :4 :5 :7 :8 :9],
    :interval-vec "555562",
    :description ""}
   {:id 303,
    :name "8-Z15..29",
    :prime-form [:0 :1 :2 :3 :4 :6 :8 :9],
    :interval-vec "555553",
    :description ""}
   {:id 304,
    :name "8-Z15B..29",
    :prime-form [:0 :1 :3 :5 :6 :7 :8 :9],
    :interval-vec "555553",
    :description ""}
   {:id 305,
    :name "8-16",
    :prime-form [:0 :1 :2 :3 :5 :7 :8 :9],
    :interval-vec "554563",
    :description ""}
   {:id 306,
    :name "8-16B",
    :prime-form [:0 :1 :2 :4 :6 :7 :8 :9],
    :interval-vec "554563",
    :description ""}
   {:id 307,
    :name "8-17*",
    :prime-form [:0 :1 :3 :4 :5 :6 :8 :9],
    :interval-vec "546652",
    :description ""}
   {:id 308,
    :name "8-18",
    :prime-form [:0 :1 :2 :3 :5 :6 :8 :9],
    :interval-vec "546553",
    :description ""}
   {:id 309,
    :name "8-18B",
    :prime-form [:0 :1 :3 :4 :6 :7 :8 :9],
    :interval-vec "546553",
    :description ""}
   {:id 310,
    :name "8-19",
    :prime-form [:0 :1 :2 :4 :5 :6 :8 :9],
    :interval-vec "545752",
    :description ""}
   {:id 311,
    :name "8-19B",
    :prime-form [:0 :1 :3 :4 :5 :7 :8 :9],
    :interval-vec "545752",
    :description ""}
   {:id 312,
    :name "8-20*",
    :prime-form [:0 :1 :2 :4 :5 :7 :8 :9],
    :interval-vec "545662",
    :description ""}
   {:id 313,
    :name "8-21*",
    :prime-form [:0 :1 :2 :3 :4 :6 :8 :૪],
    :interval-vec "474643",
    :description ""}
   {:id 314,
    :name "8-22",
    :prime-form [:0 :1 :2 :3 :5 :6 :8 :૪],
    :interval-vec "465562",
    :description ""}
   {:id 315,
    :name "8-22B",
    :prime-form [:0 :1 :2 :3 :5 :7 :9 :૪],
    :interval-vec "465562",
    :description "Spanish Octatonic Scale (r9) (12111222)"}
   {:id 316,
    :name "8-23*",
    :prime-form [:0 :1 :2 :3 :5 :7 :8 :૪],
    :interval-vec "465472",
    :description "Quartal Octachord, Diatonic Octad"}
   {:id 317,
    :name "8-24*",
    :prime-form [:0 :1 :2 :4 :5 :6 :8 :૪],
    :interval-vec "464743",
    :description ""}
   {:id 318,
    :name "8-25* (6)",
    :prime-form [:0 :1 :2 :4 :6 :7 :8 :૪],
    :interval-vec "464644",
    :description "Messiaen mode 6 (11221122)"}
   {:id 319,
    :name "8-26*",
    :prime-form [:0 :1 :2 :4 :5 :7 :9 :૪],
    :interval-vec "456562",
    :description
    "Spanish Phrygian (r9) (12112122)/ Blues mode.2 (21211212)"}
   {:id 320,
    :name "8-27",
    :prime-form [:0 :1 :2 :4 :5 :7 :8 :૪],
    :interval-vec "456553",
    :description ""}
   {:id 321,
    :name "8-27B",
    :prime-form [:0 :1 :2 :4 :6 :7 :9 :૪],
    :interval-vec "456553",
    :description ""}
   {:id 322,
    :name "8-28* (3)",
    :prime-form [:0 :1 :3 :4 :6 :7 :9 :૪],
    :interval-vec "448444",
    :description
    "Alternating Octatonic or Diminished scale (12121212)"}
   {:id 323,
    :name "8-Z29..15",
    :prime-form [:0 :1 :2 :3 :5 :6 :7 :9],
    :interval-vec "555553",
    :description ""}
   {:id 324,
    :name "8-Z29B..15",
    :prime-form [:0 :2 :3 :4 :6 :7 :8 :9],
    :interval-vec "555553",
    :description ""}
   {:id 325,
    :name "9-1*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :8],
    :interval-vec "876663",
    :description "Chromatic Nonamirror"}
   {:id 326,
    :name "9-2",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :9],
    :interval-vec "777663",
    :description ""}
   {:id 327,
    :name "9-2B",
    :prime-form [:0 :2 :3 :4 :5 :6 :7 :8 :9],
    :interval-vec "777663",
    :description ""}
   {:id 328,
    :name "9-3",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :8 :9],
    :interval-vec "767763",
    :description ""}
   {:id 329,
    :name "9-3B",
    :prime-form [:0 :1 :3 :4 :5 :6 :7 :8 :9],
    :interval-vec "767763",
    :description ""}
   {:id 330,
    :name "9-4",
    :prime-form [:0 :1 :2 :3 :4 :5 :7 :8 :9],
    :interval-vec "766773",
    :description ""}
   {:id 331,
    :name "9-4B",
    :prime-form [:0 :1 :2 :4 :5 :6 :7 :8 :9],
    :interval-vec "766773",
    :description ""}
   {:id 332,
    :name "9-5",
    :prime-form [:0 :1 :2 :3 :4 :6 :7 :8 :9],
    :interval-vec "766674",
    :description ""}
   {:id 333,
    :name "9-5B",
    :prime-form [:0 :1 :2 :3 :5 :6 :7 :8 :9],
    :interval-vec "766674",
    :description ""}
   {:id 334,
    :name "9-6*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :8 :૪],
    :interval-vec "686763",
    :description ""}
   {:id 335,
    :name "9-7",
    :prime-form [:0 :1 :2 :3 :4 :5 :7 :8 :૪],
    :interval-vec "677673",
    :description "Nonatonic Blues Scale (211111212)"}
   {:id 336,
    :name "9-7B",
    :prime-form [:0 :1 :2 :3 :4 :5 :7 :9 :૪],
    :interval-vec "677673",
    :description ""}
   {:id 337,
    :name "9-8",
    :prime-form [:0 :1 :2 :3 :4 :6 :7 :8 :૪],
    :interval-vec "676764",
    :description ""}
   {:id 338,
    :name "9-8B",
    :prime-form [:0 :1 :2 :3 :4 :6 :8 :9 :૪],
    :interval-vec "676764",
    :description ""}
   {:id 339,
    :name "9-9*",
    :prime-form [:0 :1 :2 :3 :5 :6 :7 :8 :૪],
    :interval-vec "676683",
    :description "Raga Ramdasi Malhar (r2) (211122111)"}
   {:id 340,
    :name "9-10*",
    :prime-form [:0 :1 :2 :3 :4 :6 :7 :9 :૪],
    :interval-vec "668664",
    :description ""}
   {:id 341,
    :name "9-11",
    :prime-form [:0 :1 :2 :3 :5 :6 :7 :9 :૪],
    :interval-vec "667773",
    :description ""}
   {:id 342,
    :name "9-11B",
    :prime-form [:0 :1 :2 :3 :5 :6 :8 :9 :૪],
    :interval-vec "667773",
    :description "Diminishing Nonachord"}

   {:id 343,
    :name "9-12* (4)",
    :prime-form [:0 :1 :2 :4 :5 :6 :8 :9 :૪],
    :interval-vec "666963",
    :description "Tsjerepnin/Messiaen mode 3 (112112112)"}
   {:id 344,
    :name "10-1*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9],
    :interval-vec "988884",
    :description "Chromatic Decamirror"}
   {:id 345,
    :name "10-2*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :8 :૪],
    :interval-vec "898884",
    :description ""}
   {:id 346,
    :name "10-3*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :9 :૪],
    :interval-vec "889884",
    :description ""}
   {:id 347,
    :name "10-4*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :8 :9 :૪],
    :interval-vec "888984",
    :description ""}
   {:id 348,
    :name "10-5*",
    :prime-form [:0 :1 :2 :3 :4 :5 :7 :8 :9 :૪],
    :interval-vec "888894",
    :description "Major-minor mixed (r7)"}
   {:id 349,
    :name "10-6* (6)",
    :prime-form [:0 :1 :2 :3 :4 :6 :7 :8 :9 :૪],
    :interval-vec "888885",
    :description "Messiaen mode 7 (1111211112)"}
   {:id 350,
    :name "11-1*",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪],
    :interval-vec "AAAAA5",
    :description "Chromatic Undecamirror"}
   {:id 351,
    :name "12-1*(1)",
    :prime-form [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ],
    :interval-vec "CCCCC6",
    :description "Chromatic Scale/Dodecamirror (111111111111)"}])

(comment
 
 (->> pc-sets
       (map (fn [m] (assoc m
                           :prime-form
                           (vec  (map (fn [c] (cond (= c \A) :૪ (= c \B) :Ɛ :else (keyword (str c))))
                                      (:prime-form m)))))))

  ; Maybe pedantic, but should :prime-form be represented by deltas instead of pitch-classes?
  ; prime form. [s] (1) a prime set or the presumed original order of a tone row. (2) (Forte) the most compact form chosen from a set and its inverse. (3) (Solomon) a transposition of the normal order of a set such that the initial pitch number (relative) is zero. Note the conflict with Forte's meaning. The prime form of a pc set is used to catalog the set in the Table of Set Classes.
  ; https://web.archive.org/web/20170710011758/http://solomonsmusic.net/setgloss.htm

  ; Other lists exist. Would by interesting to compare
  ; http://www.huygens-fokker.org/docs/modename.html
  ; https://docs.google.com/spreadsheets/d/1op1XtGRyMZLmidD-c6AgRNt7EVRoAv9A0HTVq6rD0KA/edit#gid=2031285887
)

(defn find-pc-set "returns all pc-sets with pc-count pitch-classes with description containing search-term"
  [pc-count search-term]
  (->> pc-sets
       (filter (fn [m]
                 (and (= pc-count (count (:prime-form m)))
                      (clojure.string/includes? (clojure.string/lower-case (:description m)) search-term))))
       (map #(select-keys % [:description :id :name :prime-form]))))

#_(find-pc-set 3 "augmented")
#_(find-pc-set 3 "")

(def ^:private common-sets
{ :aug 26 :dim 23 :maj 25 :min 24
:maj7 45 :min7 64 :dim7 67 :syrian-pent 105})

(defn get-pc-set "returns the prime-form of a pc-set given a keyword"
  [keyword]
  (:prime-form (nth pc-sets
                    (get common-sets
                         keyword))))

#_(get-pc-set :maj)
#_(get-pc-set :min)
#_(get-pc-set :aug)
#_(get-pc-set :dim)
