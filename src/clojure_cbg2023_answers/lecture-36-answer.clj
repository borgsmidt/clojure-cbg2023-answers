(ns clojure-cbg2023-answers.lecture-36-answer)

; Note, this answer deliberately doesn't print its result to screen (not a
; particularly functional approach). Instead it returns the result as a value,
; which greatly improves testability.

(defn assert [test] (if (false? test) (throw (AssertionError. "test failed"))))

(def buyer-account (ref 100))
(def merchant-account (ref 0))
(def inventory (ref {}))

(def item-prices {:pen 1 :notebook 5 :backpack 10})

(defn commit
  "Commits a purchase transaction"
  [item price]
  (dosync
    (alter buyer-account #(- % price))
    (alter merchant-account #(+ % price))
    (alter inventory #(assoc % item (inc (get % item 0))))))

(defn get-state
  "Returns the current state of the program"
  []
  {:buyer-account @buyer-account
   :merchant-account @merchant-account
   :inventory @inventory})

(defn buy-item
  "Buys an item of the specified type if the buyer can afford it"
  [item]
  (let [price (get item-prices item)
        status (if (<= price @buyer-account)
                 (do (commit item price) :success)
                 :failure)]
    (assoc (get-state) :status status)))

(assert
  (= {:buyer-account 100 :merchant-account 0 :inventory {}}
    (get-state)))

(assert
  (= {:status :success :buyer-account 99 :merchant-account 1 :inventory {:pen 1}}
    (buy-item :pen)))

(assert
  (= {:status :success :buyer-account 94 :merchant-account 6 :inventory {:pen 1 :notebook 1}}
    (buy-item :notebook)))

(dotimes [_ 9] (buy-item :backpack))

(assert
  (= {:buyer-account 4 :merchant-account 96 :inventory {:pen 1 :notebook 1 :backpack 9}}
    (get-state)))

(assert
  (= {:status :success :buyer-account 3 :merchant-account 97 :inventory {:pen 2 :notebook 1 :backpack 9}}
    (buy-item :pen)))

(assert
  (= {:status :failure :buyer-account 3 :merchant-account 97 :inventory {:pen 2 :notebook 1 :backpack 9}}
    (buy-item :notebook)))
