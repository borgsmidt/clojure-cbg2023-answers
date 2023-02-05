# Tests

Q: Where on Earth are all the unit tests, human!?

A: They are embedded inline in each of the code files. Like this:

```
(defn assert [test] (if (false? test) (throw (AssertionError. "test failed"))))

(assert
  (= {:coupon "The coupon is valid" :cars {:bmw 48000 :fiat 16000}}
    (analyze-budget 50000 "my-valid-coupon")))

(assert
  (= {:coupon "The coupon is invalid" :cars {:fiat 20000}}
    (analyze-budget 50000 "some-other-coupon")))
```
