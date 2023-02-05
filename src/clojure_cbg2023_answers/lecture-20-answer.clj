(ns clojure-cbg2023-answers.lecture-20-answer)

; Note, this answer deliberately doesn't print its result to screen (not a
; particularly functional approach). Instead it returns the result as a value,
; which greatly improves testability.

(defn assert [test] (if (false? test) (throw (AssertionError. "test failed"))))

(def pet-type-to-human-age {:dog 7 :cat 5 :fish 10})

(defn human-age-of-pet
  "Returns age of the specified pet in human years"
  [pet-type age]
  (* (get pet-type-to-human-age pet-type) age))

(assert (= 0 (human-age-of-pet :dog 0)))
(assert (= 35 (human-age-of-pet :dog 5)))
(assert (= 10 (human-age-of-pet :cat 2)))
(assert (= 30 (human-age-of-pet :fish 3)))
