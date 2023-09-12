const searchLocation = () => {
    let location = $("#search_location_input").val();

    if (location === "") {
        $(".search_results_location").hide();
    } else {
        let url = `http://localhost:8080/users/food?location=${location}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                $(".search_results_location").empty(); // Clear previous results

                data.forEach(MenuList => {
                    let jobCard = `
                        <div class="col mb-4">
                            <div class="card h-100 card-hover">
                                <img th:src="@{/users/MenuListing/{id}(id=${MenuList.id})}" alt="Restaurant Image">
                                <div class="card-body d-flex flex-column">
                                    <h3 class="card-title d-flex justify-content-center" th:text="${MenuList.name}"></h3>
                                    <div class="d-flex justify-content-center">
                                        <h6 th:text="${MenuList.name}"></h6><h6 class="text-muted ml-2"></h6>
                                    </div>
                                    <div class="d-flex justify-content-center">
                                        <h6 th:text="${MenuList.price}"></h6><h6 class="text-muted ml-2"></h6>
                                    </div>
                                    <div class="mt-auto">
                                        <div class="card-footer d-flex justify-content-center bg-info">
                                            <a th:href="@{/users/RestaurantListing/{id}(id=${MenuList.id})}" class="btn btn-sm text-dark p-0">
                                                <i class="fas fa-eye text-primary mr-1"></i>View Detail
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;

                    $(".search_results_location").append(jobCard); // Append job card to search results container
                });

                $(".search_results_location").show(); // Show the search results container
            })
            .catch(error => {
                console.error(error);
            });
    }
};
