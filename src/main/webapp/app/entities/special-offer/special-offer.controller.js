(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('SpecialOfferController', SpecialOfferController);

    SpecialOfferController.$inject = ['SpecialOffer'];

    function SpecialOfferController(SpecialOffer) {

        var vm = this;

        vm.specialOffers = [];

        loadAll();

        function loadAll() {
            SpecialOffer.query(function(result) {
                vm.specialOffers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
