(ns clojure-cbg2023-answers.lecture-29-answer)

; Note, this answer deliberately doesn't print its result to screen (not a
; particularly functional approach). Instead it returns the result as a value,
; which greatly improves testability.

(defn assert [test] (if (false? test) (throw (AssertionError. "test failed"))))

(def car-prices {:bmw 60000 :ferrari 100000 :fiat 20000})

(def coupon-discount-pct 20)
(def valid-coupon-code "my-valid-coupon")

(defn analyze-budget
  "Returns an analysis of the specified budget and coupon code"
  [budget code]
  (let
    [coupon-valid (= code valid-coupon-code)
     factor (if coupon-valid (- 1 (/ coupon-discount-pct 100)) 1)
     adjusted (map (fn [[car price]] [car (* price factor)]) car-prices)
     affordable (filter (fn [[car price]] (<= price budget)) adjusted)]
    {:coupon (if coupon-valid "The coupon is valid" "The coupon is invalid")
     :cars (into {} affordable)}))

(assert
  (= {:coupon "The coupon is valid" :cars {:bmw 48000 :fiat 16000}}
    (analyze-budget 50000 "my-valid-coupon")))

(assert
  (= {:coupon "The coupon is invalid" :cars {:fiat 20000}}
    (analyze-budget 50000 "some-other-coupon")))
