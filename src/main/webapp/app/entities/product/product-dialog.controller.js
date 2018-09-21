(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product', 'SpecialOffer', 'Supplier', 'PriceRule'];

    function ProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Product, SpecialOffer, Supplier, PriceRule) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.save = save;
        vm.specialoffers = SpecialOffer.query();
        vm.suppliers = Supplier.query();
        vm.pricerules = PriceRule.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artemisApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
