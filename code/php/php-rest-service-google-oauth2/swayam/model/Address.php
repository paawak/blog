<?php

namespace swayam\model;

use Doctrine\ORM\Mapping\Embeddable;
use Doctrine\ORM\Mapping\Column;

/**
 * @Embeddable 
 */
class Address implements \JsonSerializable {

    /**
     * @Column(type="string") 
     */
    private $address;

    /**
     * @Column(type="string") 
     */
    private $city;

    /**
     * @Column(type="string") 
     */
    private $state;

    /**
     * @Column(name = "zip_code", type="string") 
     */
    private $zipCode;

    /**
     * @Column(type="string") 
     */
    private $country;

    public function getAddress() {
        return $this->address;
    }

    public function getCity() {
        return $this->city;
    }

    public function getState() {
        return $this->state;
    }

    public function getZipCode() {
        return $this->zipCode;
    }

    public function getCountry() {
        return $this->country;
    }

    public function setAddress($address): void {
        $this->address = $address;
    }

    public function setCity($city): void {
        $this->city = $city;
    }

    public function setState($state): void {
        $this->state = $state;
    }

    public function setZipCode($zipCode): void {
        $this->zipCode = $zipCode;
    }

    public function setCountry($country): void {
        $this->country = $country;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }

    public static function fromJsonArray($addressAsArray) {
        $address = new Address();
        foreach ($addressAsArray as $fieldName => $value) {
            $address->{$fieldName} = $value;
        }
        return $address;
    }

}
